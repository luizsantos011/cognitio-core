package com.cognitio.core.event;

import com.cognitio.api.perception.TransmutationAPI;
import com.cognitio.core.CognitioCore;
import com.cognitio.core.perception.PerceptionEngine;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class InventoryTransmutationHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        // Run once per second (every 20 ticks) to avoid heavy load
        if (event.getEntity().level().getGameTime() % 20 != 0) return;

        Player player = event.getEntity();
        
        // Creative players ignore transmutation (can hold both items)
        if (player.isCreative()) return;

        int insight = PerceptionEngine.getEffectivePerception(player);

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;

            for (TransmutationAPI.TransmutationRule rule : TransmutationAPI.getRules()) {
                // If holding Original and HAS insight -> transmutates to Target
                if (stack.is(rule.original()) && insight >= rule.tier().getRequiredInsight()) {
                    ItemStack newStack = new ItemStack(rule.target(), stack.getCount());
                    newStack.applyComponents(stack.getComponents());
                    player.getInventory().setItem(i, newStack);
                    break;
                }
                // If holding Target and LACKS insight -> reverts to Original
                else if (stack.is(rule.target()) && insight < rule.tier().getRequiredInsight()) {
                    ItemStack newStack = new ItemStack(rule.original(), stack.getCount());
                    newStack.applyComponents(stack.getComponents());
                    player.getInventory().setItem(i, newStack);
                    break;
                }
            }
        }
    }
}
