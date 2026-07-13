package com.cognitio.core.influence;

import com.cognitio.api.influence.InfluenceEvent;

import net.neoforged.bus.api.SubscribeEvent;

public class InfluenceOntologicalHandler {

    @SubscribeEvent
    public void onOntologicalInfluence(InfluenceEvent.Ontological event) {
        net.minecraft.world.entity.player.Player player = event.getPlayer();
        if (!(player.level() instanceof net.minecraft.server.level.ServerLevel level)) return;

        int perception = event.getCurrentPerception();
        
        // Aura Devastadora: Insight alto corrompe a natureza ao redor
        if (perception >= 75) {
            if (player.getRandom().nextDouble() < 0.2) {
                net.minecraft.core.BlockPos pos = player.blockPosition().offset(
                    player.getRandom().nextInt(7) - 3, 
                    player.getRandom().nextInt(3) - 1, 
                    player.getRandom().nextInt(7) - 3
                );
                
                net.minecraft.world.level.block.state.BlockState state = level.getBlockState(pos);
                if (state.is(net.minecraft.world.level.block.Blocks.WHEAT) || state.is(net.minecraft.world.level.block.Blocks.CARROTS) || state.is(net.minecraft.world.level.block.Blocks.POTATOES)) {
                    level.destroyBlock(pos, false); // A planta murcha sem dropar itens
                } else if (state.is(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)) {
                    level.setBlockAndUpdate(pos, net.minecraft.world.level.block.Blocks.DIRT.defaultBlockState());
                }
            }
        }
    }
}