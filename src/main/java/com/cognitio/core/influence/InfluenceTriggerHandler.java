package com.cognitio.core.influence;

import com.cognitio.api.influence.InfluenceEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class InfluenceTriggerHandler {

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            NeoForge.EVENT_BUS.post(new InfluenceEvent.Psychic(player));
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(FinalizeSpawnEvent event) {
        if (event.getLevel() != null && !event.getLevel().isClientSide()) {
            for (Player player : event.getLevel().players()) {
                NeoForge.EVENT_BUS.post(new InfluenceEvent.Relational(player));
            }
        }
    }
}