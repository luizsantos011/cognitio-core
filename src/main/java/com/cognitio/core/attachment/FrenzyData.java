package com.cognitio.core.attachment;

import com.mojang.serialization.Codec;

public record FrenzyData(float amount) {
    public static final Codec<FrenzyData> CODEC = Codec.FLOAT.xmap(FrenzyData::new, FrenzyData::amount);
}
