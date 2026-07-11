package com.cognitio.core.influence;

import com.cognitio.api.influence.InfluenceEvent;

import net.neoforged.bus.api.SubscribeEvent;

public class InfluenceMentalHandler {

    @SubscribeEvent
    public void onPsychicInfluence(InfluenceEvent.Psychic event) {
        // Player player = event.getPlayer();
        int perception = event.getCurrentPerception();

        if (perception >= 75) {
            // TODO: Gatilho para percepção máxima (Visão da Realidade Distorcida / Efeitos Avançados)
        } else if (perception >= 50) {
            // TODO: Gatilho para percepção média (Sons ambientes sutis / Partículas customizadas)
        } else if (perception >= 25) {
            // TODO: Gatilho para percepção inicial (Primeiros vislumbres)
        }
    }
}