package com.cognitio.core.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientInsightCache {
    private static int currentPoints = 0;

    public static void setPoints(int points) {
        currentPoints = points;
    }

    public static int getPoints() {
        return currentPoints;
    }
}
