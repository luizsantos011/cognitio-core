package com.cognitio.core.event;

import com.cognitio.api.perception.IInsightEntity;
import com.cognitio.core.CognitioCore;
import com.cognitio.core.perception.PerceptionEngine;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class HiddenEntityManager {

    /**
     * Impede que o jogador bata em uma entidade que ele não tem Insight para ver.
     */
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        if (event.getTarget() instanceof IInsightEntity insightEntity) {
            Player player = event.getEntity();
            int currentInsight = PerceptionEngine.getEffectivePerception(player);
            
            if (currentInsight < insightEntity.getRequiredInsight()) {
                event.setCanceled(true); // Impede o hit
            }
        }
    }

    /**
     * Impede interação de clique direito com a entidade invisível.
     */
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof IInsightEntity insightEntity) {
            Player player = event.getEntity();
            int currentInsight = PerceptionEngine.getEffectivePerception(player);
            
            if (currentInsight < insightEntity.getRequiredInsight()) {
                event.setCanceled(true);
            }
        }
    }

    /**
     * Impede que a entidade ataque o jogador se o jogador não tiver Insight para percebê-la.
     */
    @SubscribeEvent
    public static void onEntityTick(net.neoforged.neoforge.event.tick.EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof IInsightEntity insightEntity && event.getEntity() instanceof net.minecraft.world.entity.Mob mob) {
            if (mob.getTarget() instanceof Player player) {
                int currentInsight = PerceptionEngine.getEffectivePerception(player);
                
                if (currentInsight < insightEntity.getRequiredInsight()) {
                    mob.setTarget(null); // Entidade desiste de focar no jogador
                }
            }
        }
    }
}
