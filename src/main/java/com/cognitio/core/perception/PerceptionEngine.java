package com.cognitio.core.perception;

import com.cognitio.api.influence.InfluenceEvent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PerceptionEngine {

    private static final Map<UUID, PerceptionTier> playerTiers = new HashMap<>();

    public enum PerceptionTier {
        NONE(0),
        MEDIUM(50),
        ADVANCED(75),
        OBSESSED(1000);

        public final int minPerception;

        PerceptionTier(int minPerception) {
            this.minPerception = minPerception;
        }

        public static PerceptionTier getTierFor(int perception) {
            if (perception >= 1000) return OBSESSED;
            if (perception >= 75) return ADVANCED;
            if (perception >= 50) return MEDIUM;
            return NONE;
        }
    }

    public static void updatePlayerPerception(ServerPlayer player, int newPerceptionValue) {
        UUID uuid = player.getUUID();
        PerceptionTier currentTier = PerceptionTier.getTierFor(newPerceptionValue);
        PerceptionTier lastTier = playerTiers.getOrDefault(uuid, PerceptionTier.NONE);

        if (currentTier != lastTier) {
            playerTiers.put(uuid, currentTier);

            NeoForge.EVENT_BUS.post(new InfluenceEvent.Psychic(player));
            NeoForge.EVENT_BUS.post(new InfluenceEvent.Relational(player));
            NeoForge.EVENT_BUS.post(new InfluenceEvent.Ontological(player));
        }
    }
}