package com.cognitio;

import net.minecraft.world.entity.player.Player;

public class PerspectiveEngine {
    public static int getEffectivePerspective(Player player){
        InsightData insightData = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
        int rawInsight = insightData.points();
        int basePerspective = rawInsight;
        double multiplier = getPerspectiveMultiplier(player);
        int bonus = getPerspectiveBonus(player);
        int finalPerspective = (int) (basePerspective * multiplier + bonus);

        return Math.max(0, finalPerspective);
    }

    private static double getPerspectiveMultiplier(Player player){
        return 1.0;
    }

    private static int getPerspectiveBonus(Player player){
        return 0;
    }
}
