package com.cognitio.core.influence;

import com.cognitio.api.influence.InfluenceEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;

public class InfluenceRelationalHandler {

    @SubscribeEvent
    public void onRelationalInfluence(InfluenceEvent.Relational event) {
        Player player = event.getPlayer();
        int perception = event.getCurrentPerception();

        if (perception >= 75) {
            // TODO: Gatilho para interações avançadas (Mudança drástica de comportamento dos mobs)
        } else if (perception >= 50) {
            // TODO: Gatilho para interações médias (Hostilidade ou atração baseada no Insight)
        } else if (perception >= 25) {
            // TODO: Gatilho para interações iniciais (Reações sutis do ambiente)
        }
    }
}