package com.cognitio.core.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Map;
import java.util.Collections;

public record InsightData(
        int points,
        Map <String, Double> multipliers,
        Map<String, Integer> bonuses) {

    public static final Codec<InsightData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("points").forGetter(InsightData::points),
            Codec.unboundedMap(Codec.STRING, Codec.DOUBLE).fieldOf("multipliers").forGetter(InsightData::multipliers),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("bonuses").forGetter(InsightData::bonuses)
    ).apply(instance, InsightData::new));

    public InsightData(int points) {
        this(points, Collections.emptyMap(), Collections.emptyMap());
    }
}


