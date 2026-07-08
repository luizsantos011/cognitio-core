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
}
