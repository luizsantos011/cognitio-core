package com.cognitio.core.network;

import com.cognitio.core.CognitioCore;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncFrenzyPayload(float amount) implements CustomPacketPayload {
    public static final Type<SyncFrenzyPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CognitioCore.MODID, "sync_frenzy"));

    public static final StreamCodec<FriendlyByteBuf, SyncFrenzyPayload> STREAM_CODEC = StreamCodec.ofMember(
            SyncFrenzyPayload::write,
            SyncFrenzyPayload::new
    );

    public SyncFrenzyPayload(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(amount);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
