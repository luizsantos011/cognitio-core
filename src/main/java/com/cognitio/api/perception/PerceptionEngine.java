package com.cognitio.api.perception;

import com.cognitio.attachment.AttachmentRegister;
import com.cognitio.attachment.InsightData;
import com.cognitio.core.network.SyncInsightPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class PerceptionEngine {

    public static int getEffectivePerception(Player player) {
        InsightData insightData = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
        int basePerception = insightData.points();
        int totalWithBonus = basePerception + getPerceptionBonus(insightData);
        double totalMultiplier = getPerceptionMultiplier(insightData);
        int finalPerception = (int) (totalWithBonus * totalMultiplier);

        return Math.max(0, finalPerception);
    }

    private static double getPerceptionMultiplier(InsightData insightData) {
        double total = 1.0;

        for (double mod : insightData.multipliers().values()) {
            total *= mod;
        }
        return total;
    }

    private static int getPerceptionBonus(InsightData insightData) {
        int total = 0;

        for (int bonus : insightData.bonuses().values()) {
            total += bonus;
        }
        return total;
    }

    public static void applyModifier(Player player, String id, double multiplier, int bonus) {
        InsightData oldData = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
        Map<String, Double> newMultipliers = new HashMap<>(oldData.multipliers());
        Map<String, Integer> newBonuses = new HashMap<>(oldData.bonuses());

        newMultipliers.put(id, multiplier);
        newBonuses.put(id, bonus);

        InsightData newData = new InsightData(oldData.points(), newMultipliers, newBonuses);
        player.setData(AttachmentRegister.COGNITIO_INSIGHT.get(), newData);

        syncAndRefresh(player, newData.points());
    }

    public static void removeModifier(Player player, String id) {
        InsightData oldData = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
        Map<String, Double> newMultipliers = new HashMap<>(oldData.multipliers());
        Map<String, Integer> newBonuses = new HashMap<>(oldData.bonuses());

        newMultipliers.remove(id);
        newBonuses.remove(id);

        InsightData newData = new InsightData(oldData.points(), newMultipliers, newBonuses);
        player.setData(AttachmentRegister.COGNITIO_INSIGHT.get(), newData);

        syncAndRefresh(player, newData.points());
    }

    public static boolean addInsight(Player player, int amount, InsightSource source) {
        InsightEvent.Gain event = new InsightEvent.Gain(player, amount, source);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            return false;
        }

        int finalAmount = event.getNewAmount();
        if (finalAmount <= 0) return false;

        InsightData oldData = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
        InsightData newData = new InsightData(oldData.points() + finalAmount, oldData.multipliers(), oldData.bonuses());
        player.setData(AttachmentRegister.COGNITIO_INSIGHT.get(), newData);

        syncAndRefresh(player, newData.points());
        return true;
    }

    private static void syncAndRefresh(Player player, int points) {
        if (player instanceof ServerPlayer serverPlayer) {
            com.cognitio.core.perception.PerceptionEngine.updatePlayerPerception(serverPlayer, getEffectivePerception(player));

            PacketDistributor.sendToPlayer(serverPlayer, new SyncInsightPayload(points));
            serverPlayer.inventoryMenu.broadcastChanges();
        }
    }
}