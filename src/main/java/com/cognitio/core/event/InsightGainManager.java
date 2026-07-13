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
            net.minecraft.resources.ResourceLocation itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(event.getItem().getItem());
            if (com.cognitio.core.data.InsightSourceManager.ITEM_GAINS.containsKey(itemId)) {
                int amount = com.cognitio.core.data.InsightSourceManager.ITEM_GAINS.get(itemId);
                InsightAPI.grantInsight(player, amount, InsightSource.CONSUMABLE);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide()) {
            net.minecraft.resources.ResourceLocation entityId = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
            if (com.cognitio.core.data.InsightSourceManager.ENTITY_KILLS.containsKey(entityId)) {
                int amount = com.cognitio.core.data.InsightSourceManager.ENTITY_KILLS.get(entityId);
                InsightAPI.grantInsight(player, amount, InsightSource.ENTITY_KILL);
            }
        }
    }
}
