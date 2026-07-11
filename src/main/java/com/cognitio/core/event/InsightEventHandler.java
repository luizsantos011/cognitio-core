package com.cognitio.core.event;

import com.cognitio.core.perception.PerceptionEngine;

import com.cognitio.core.CognitioCore;
import net.minecraft.core.BlockPos;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class InsightEventHandler {


    @SubscribeEvent
    public static void onBlockDrop(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (!event.getLevel().isClientSide() && event.getState().is(Blocks.GRASS_BLOCK)) {
            int currentPerception = PerceptionEngine.getEffectivePerception(player);

            if (currentPerception >= 10) {
                if (event.getLevel() instanceof Level level) {
                    BlockPos pos = event.getPos();

                    ItemStack seedStack = new ItemStack(Items.WHEAT_SEEDS, 1);
                    Block.popResource(level, pos, seedStack);
                }
            }
        }
    }
}


