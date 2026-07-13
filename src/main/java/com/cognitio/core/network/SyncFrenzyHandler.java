package com.cognitio.core.network;

import com.cognitio.core.client.ClientFrenzyCache;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncFrenzyHandler {
    public static void handle(SyncFrenzyPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientFrenzyCache.setFrenzy(payload.amount());
        });
    }
}
