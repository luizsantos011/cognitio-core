package com.cognitio.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record InsightData(int points) {
    public static final Codec<InsightData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("points").forGetter(InsightData::points)).apply(instance, InsightData::new)
    );
}
