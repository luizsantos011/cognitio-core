package com.cognitio.core.client;

import com.cognitio.core.CognitioCore;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelSwapHandler {

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        // Pega o ID da Sopa de Cogumelo
        ModelResourceLocation soupLocation = new ModelResourceLocation(ResourceLocation.withDefaultNamespace("mushroom_stew"), "inventory");

        BakedModel originalSoup = event.getModels().get(soupLocation);

        if (originalSoup != null) {
            // Substitui pelo nosso modelo dinâmico
            event.getModels().put(soupLocation, new InsightItemModel(originalSoup, event.getModels()));
        }
    }
}