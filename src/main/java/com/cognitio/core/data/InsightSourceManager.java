package com.cognitio.core.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class InsightSourceManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    
    public static final Map<ResourceLocation, com.cognitio.core.data.InsightSourceData> ITEM_GAINS = new HashMap<>();
    public static final Map<ResourceLocation, com.cognitio.core.data.InsightSourceData> ENTITY_KILLS = new HashMap<>();

    public InsightSourceManager() {
        super(GSON, "insight_sources"); // Lê de data/<namespace>/insight_sources/*.json
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        ITEM_GAINS.clear();
        ENTITY_KILLS.clear();

        object.forEach((location, jsonElement) -> {
            try {
                if (jsonElement.isJsonObject()) {
                    var json = jsonElement.getAsJsonObject();
                    String type = json.get("type").getAsString(); // "item" ou "entity"
                    String target = json.get("target").getAsString(); // ex: "minecraft:spider_eye"
                    
                    int tier = json.has("tier") ? json.get("tier").getAsInt() : 1;
                    int amount = json.has("amount") ? json.get("amount").getAsInt() : (tier == 1 ? 1 : tier * 5); // Default scale
                    
                    ResourceLocation targetLoc = ResourceLocation.parse(target);
                    com.cognitio.core.data.InsightSourceData data = new com.cognitio.core.data.InsightSourceData(tier, amount);

                    if ("item".equals(type)) {
                        ITEM_GAINS.put(targetLoc, data);
                    } else if ("entity".equals(type)) {
                        ENTITY_KILLS.put(targetLoc, data);
                    }
                }
            } catch (Exception e) {
                com.cognitio.core.CognitioCore.LOGGER.error("Erro ao ler fonte de insight guiada a dados (JSON): " + location, e);
            }
        });
        
        com.cognitio.core.CognitioCore.LOGGER.info("Carregado " + (ITEM_GAINS.size() + ENTITY_KILLS.size()) + " fontes de Insight customizadas (Data-Driven).");
    }
}
