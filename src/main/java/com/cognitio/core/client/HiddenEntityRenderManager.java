package com.cognitio.core.client;

import com.cognitio.api.perception.IInsightEntity;
import com.cognitio.core.CognitioCore;
import com.cognitio.core.perception.PerceptionEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class HiddenEntityRenderManager {

    /**
     * Cancela a renderização de entidades caso o jogador local não possua Insight suficiente para percebê-las.
     */
    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        if (event.getEntity() instanceof IInsightEntity insightEntity) {
            Player localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null) {
                int effectiveInsight = PerceptionEngine.getEffectivePerception(localPlayer);
                
                if (effectiveInsight < insightEntity.getRequiredInsight()) {
                    event.setCanceled(true); // Mob fica completamente invisível
                }
            }
        }
    }
}
