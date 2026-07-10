package com.cognitio.core.network;

import com.cognitio.api.perception.PerceptionEngine;
import com.cognitio.core.CognitioCore;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = CognitioCore.MODID)
public class SyncEventHandler {

    // Sincroniza ao entrar no mundo
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            int points = PerceptionEngine.getEffectivePerception(serverPlayer);
            PacketDistributor.sendToPlayer(serverPlayer, new SyncInsightPayload(points));
        }
    }

    // Sincroniza ao morrer e dar respawn, ou voltar do The End
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            int points = PerceptionEngine.getEffectivePerception(serverPlayer);
            PacketDistributor.sendToPlayer(serverPlayer, new SyncInsightPayload(points));
        }
    }
}