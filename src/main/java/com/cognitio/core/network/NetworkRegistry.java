package com.cognitio.core.network;

import com.cognitio.core.CognitioCore;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkRegistry {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0");
        registrar.playToClient(
                SyncInsightPayload.TYPE,
                SyncInsightPayload.STREAM_CODEC,
                SyncInsightHandler::handle
        );
        registrar.playToClient(
                SyncFrenzyPayload.TYPE,
                SyncFrenzyPayload.STREAM_CODEC,
                SyncFrenzyHandler::handle
        );
    }
}