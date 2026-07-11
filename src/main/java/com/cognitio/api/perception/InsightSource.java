package com.cognitio.api.perception;

import net.minecraft.resources.ResourceLocation;

/**
 * Representa a origem de um ganho ou perda de Insight.
 * Útil para que outros mods saibam de onde veio a sanidade e possam interceptar de forma específica.
 */
public record InsightSource(ResourceLocation id) {

    // Gatilhos comuns do vanilla
    public static final InsightSource BLOCK_BREAK = new InsightSource(ResourceLocation.withDefaultNamespace("block_break"));
    public static final InsightSource ENTITY_KILL = new InsightSource(ResourceLocation.withDefaultNamespace("entity_kill"));
    public static final InsightSource CONSUMABLE = new InsightSource(ResourceLocation.withDefaultNamespace("consumable"));
    public static final InsightSource COMMAND = new InsightSource(ResourceLocation.withDefaultNamespace("command"));

    public InsightSource(String namespace, String path) {
        this(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }
}
