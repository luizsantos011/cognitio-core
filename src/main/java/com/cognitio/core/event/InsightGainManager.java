package com.cognitio.core.event;

import com.cognitio.api.perception.InsightAPI;
import com.cognitio.api.perception.InsightSource;
import com.cognitio.core.CognitioCore;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class InsightGainManager {

    @SubscribeEvent
    public static void onItemConsumed(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            // Comer um olho de aranha (cru ou fermentado) dá Insight
            if (event.getItem().is(Items.SPIDER_EYE) || event.getItem().is(Items.FERMENTED_SPIDER_EYE)) {
                InsightAPI.grantInsight(player, 1, InsightSource.CONSUMABLE);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide()) {
            LivingEntity killedEntity = event.getEntity();

            // Matar um Enderman concede uma faísca de Insight
            if (killedEntity.getType() == EntityType.ENDERMAN) {
                InsightAPI.grantInsight(player, 5, InsightSource.ENTITY_KILL);
            }
        }
    }
}
