package com.cognitio.core.client;

import com.cognitio.api.perception.IInsightItem;
import com.cognitio.core.CognitioCore;
import com.cognitio.core.perception.PerceptionEngine;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = CognitioCore.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class HiddenTextManager {

    /**
     * Intercepta a renderização de Tooltips de itens que exigem Insight.
     * Se o jogador não tiver o Insight necessário, todo o texto vira jargão alienígena (Obfuscated).
     */
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof IInsightItem insightItem) {
            Player localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null) {
                int effectiveInsight = PerceptionEngine.getEffectivePerception(localPlayer);
                
                if (effectiveInsight < insightItem.getRequiredInsight()) {
                    List<Component> tooltip = event.getToolTip();
                    
                    for (int i = 0; i < tooltip.size(); i++) {
                        Component original = tooltip.get(i);
                        // Substitui o componente original por um completamente ofuscado
                        MutableComponent obfuscated = Component.literal(original.getString())
                                .withStyle(ChatFormatting.OBFUSCATED)
                                .withStyle(ChatFormatting.GRAY);
                        tooltip.set(i, obfuscated);
                    }
                }
            }
        }
    }
}
