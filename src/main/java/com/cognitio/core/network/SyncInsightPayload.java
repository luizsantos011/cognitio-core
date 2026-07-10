package com.cognitio.core.network;

import com.cognitio.core.CognitioCore;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncInsightPayload(int insightPoints) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncInsightPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CognitioCore.MODID, "sync_insight"));
    public static final StreamCodec<FriendlyByteBuf, SyncInsightPayload> STREAM_CODEC = StreamCodec.ofMember(SyncInsightPayload::write, SyncInsightPayload::new);

    public SyncInsightPayload(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.insightPoints);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}