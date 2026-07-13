package com.cognitio.api.perception;

/**
 * Define o nível de esclarecimento de um evento ou item, e o limite máximo
 * de Insight que ele pode conceder a um jogador (Diminishing Returns).
 */
public enum EnlightenmentTier {
    TIER_1(1, 100),
    TIER_2(2, 500),
    TIER_3(3, 1000),
    TIER_4(4, Integer.MAX_VALUE);

    private final int level;
    private final int hardCap;

    EnlightenmentTier(int level, int hardCap) {
        this.level = level;
        this.hardCap = hardCap;
    }

    public int getLevel() {
        return level;
    }

    public int getHardCap() {
        return hardCap;
    }

    public static EnlightenmentTier fromLevel(int level) {
        for (EnlightenmentTier tier : values()) {
            if (tier.getLevel() == level) {
                return tier;
            }
        }
        return TIER_1; // Padrão se não encontrado
    }
}
