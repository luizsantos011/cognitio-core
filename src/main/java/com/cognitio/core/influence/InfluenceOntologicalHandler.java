package com.cognitio.core.influence;

import com.cognitio.api.influence.InfluenceEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;

public class InfluenceOntologicalHandler {

    @SubscribeEvent
    public void onOntologicalInfluence(InfluenceEvent.Ontological event) {
        Player player = event.getPlayer();
        int perception = event.getCurrentPerception();

        if (perception >= 1000) {
            // TODO: Manipular o tempo do dia (level.setDayTime)
        } else if (perception >= 75) {
            // TODO: Alterações físicas avançadas
        } else if (perception >= 50) {
            // TODO: Alterações físicas médias
        }
    }
}