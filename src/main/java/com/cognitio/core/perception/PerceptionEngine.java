package com.cognitio.core.perception;

import com.cognitio.api.influence.InfluenceEvent;
import com.cognitio.core.CognitioCore;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = CognitioCore.MODID)
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

    public static boolean hasObsessedPlayer = false;

    public static void updatePlayerPerception(ServerPlayer player, int newPerceptionValue) {
        UUID uuid = player.getUUID();
        PerceptionTier currentTier = PerceptionTier.getTierFor(newPerceptionValue);
        PerceptionTier lastTier = playerTiers.getOrDefault(uuid, PerceptionTier.NONE);

        if (currentTier != lastTier) {
            playerTiers.put(uuid, currentTier);
            hasObsessedPlayer = playerTiers.values().stream().anyMatch(tier -> tier == PerceptionTier.OBSESSED);

            // Aplica o efeito visual apenas se o jogador subiu de tier (subindo de 0 para 50)
            if (currentTier.minPerception > lastTier.minPerception) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, false, false, true));
            }

            NeoForge.EVENT_BUS.post(new InfluenceEvent.Psychic(player));
            NeoForge.EVENT_BUS.post(new InfluenceEvent.Relational(player));
            NeoForge.EVENT_BUS.post(new InfluenceEvent.Ontological(player));
        }
    }

    // Impede que dados de sessões antigas fiquem presos na memória da IDE ao trocar de mundo
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        playerTiers.remove(event.getEntity().getUUID());
        hasObsessedPlayer = playerTiers.values().stream().anyMatch(tier -> tier == PerceptionTier.OBSESSED);
    }
}