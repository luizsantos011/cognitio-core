package com.cognitio.api.perception;

import com.cognitio.attachment.AttachmentRegister;
import com.cognitio.attachment.InsightData;
import net.minecraft.world.entity.player.Player;

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

        for(double mod : insightData.multipliers().values()){
            total *= mod;
        }
        return total;
    }

    private static int getPerceptionBonus(InsightData insightData) {
        int total = 0;

        for(int bonus : insightData.bonuses().values()){
            total += bonus;
        }
        return total;
    }
}