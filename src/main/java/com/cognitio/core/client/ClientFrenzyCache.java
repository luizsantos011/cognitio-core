package com.cognitio.core.client;

public class ClientFrenzyCache {
    private static float frenzy = 0f;

    public static void setFrenzy(float f) {
        frenzy = f;
    }

    public static float getFrenzy() {
        return frenzy;
    }
}
