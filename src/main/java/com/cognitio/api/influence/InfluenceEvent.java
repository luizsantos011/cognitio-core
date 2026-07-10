package com.cognitio.api.influence;

import com.cognitio.api.perception.PerceptionEngine;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

public abstract class InfluenceEvent extends Event {

    private final Player player;
    private final int currentPerception;

    public InfluenceEvent(Player player) {
        this.player = player;
        this.currentPerception = PerceptionEngine.getEffectivePerception(player);
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getCurrentPerception() {
        return this.currentPerception;
    }

    public static class Psychic extends InfluenceEvent {
        public Psychic(Player player) {
            super(player);
        }
    }

    public static class Ontological extends InfluenceEvent {
        public Ontological(Player player) {
            super(player);
        }
    }

    public static class Relational extends InfluenceEvent {
        public Relational(Player player) {
            super(player);
        }
    }
}