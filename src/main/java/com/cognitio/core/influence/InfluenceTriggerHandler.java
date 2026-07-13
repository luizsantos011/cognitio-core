package com.cognitio.core.influence;

import com.cognitio.api.influence.InfluenceEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class InfluenceTriggerHandler {

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide() && player.tickCount % 20 == 0) {
            NeoForge.EVENT_BUS.post(new InfluenceEvent.Psychic(player));
        }
    }
}