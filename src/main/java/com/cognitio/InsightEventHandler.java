package com.cognitio;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.*;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class InsightEventHandler {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if(!event.getLevel().isClientSide() && !player.isCreative()) {
            InsightData oldData = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());

            int currentPoints = oldData.points();
            int newPoints = currentPoints + 1;
            InsightData newData = new InsightData(newPoints);

            player.setData(AttachmentRegister.COGNITIO_INSIGHT.get(), newData);
            player.sendSystemMessage(Component.literal("Você ganhou 1 ponto de Insight! Total: " + newPoints));
        }
    }

    @SubscribeEvent
    public static void onBlockDrop(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if(!event.getLevel().isClientSide() && event.getState().is(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)) {
            int currentPerspective = PerspectiveEngine.getEffectivePerspective(player);

            if(currentPerspective >= 10){
                net.minecraft.world.level.Level level = (net.minecraft.world.level.Level) event.getLevel();
                net.minecraft.core.BlockPos pos = event.getPos();
                net.minecraft.world.entity.item.ItemEntity seedDrop = new net.minecraft.world.entity.item.ItemEntity(
                        level,
                        pos.getX(), pos.getY(), pos.getZ(),
                        new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.WHEAT_SEEDS, 1));
                level.addFreshEntity(seedDrop);
            }
        }
    }
}
