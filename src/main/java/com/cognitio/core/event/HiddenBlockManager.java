package com.cognitio.core.event;

import com.cognitio.api.perception.IInsightBlock;
import com.cognitio.core.CognitioCore;
import com.cognitio.core.perception.PerceptionEngine;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class HiddenBlockManager {

    /**
     * Impede que o jogador interaja (clique direito) com um bloco mágico sem ter o Insight necessário.
     */
    @SubscribeEvent
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        BlockState state = event.getLevel().getBlockState(event.getPos());
        if (state.getBlock() instanceof IInsightBlock insightBlock) {
            Player player = event.getEntity();
            int currentInsight = PerceptionEngine.getEffectivePerception(player);
            
            if (currentInsight < insightBlock.getRequiredInsight()) {
                event.setCanceled(true); // Finge que é uma parede oca
            }
        }
    }

    /**
     * Impede que o jogador quebre acidentalmente um bloco mágico sem ter o Insight necessário.
     */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        if (state.getBlock() instanceof IInsightBlock insightBlock) {
            Player player = event.getPlayer();
            int currentInsight = PerceptionEngine.getEffectivePerception(player);
            
            if (currentInsight < insightBlock.getRequiredInsight()) {
                event.setCanceled(true); // Bloco indestrutível para quem não tem insight
            }
        }
    }
}
