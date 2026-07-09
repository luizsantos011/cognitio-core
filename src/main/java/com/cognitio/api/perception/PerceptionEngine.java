package com.cognitio.api.perception;

import com.cognitio.attachment.AttachmentRegister;
import com.cognitio.attachment.InsightData;
import net.minecraft.world.entity.player.Player;

public class PerceptionEngine {

    public static int getEffectivePerception(Player player) {
        InsightData insightData = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
        int rawInsight = insightData.points();
        int basePerception = rawInsight;

        double multiplier = getPerceptionMultiplier(player);
        int bonus = getPerceptionBonus(player);

        int finalPerception = (int) (basePerception * multiplier + bonus);

        return Math.max(0, finalPerception);
    }

    private static double getPerceptionMultiplier(Player player) {
        return 1.0;
    }

    private static int getPerceptionBonus(Player player) {
        return 0;
    }
}