package com.cognitio.api.perception;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * Eventos relacionados à percepção (Insight) do jogador.
 * Permite interceptar ganhos e perdas de sanidade.
 */
public abstract class InsightEvent extends Event {

    private final Player player;

    public InsightEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Disparado quando o jogador está prestes a ganhar Insight.
     * Pode ser cancelado para impedir o ganho, ou o valor pode ser modificado.
     */
    public static class Gain extends InsightEvent implements ICancellableEvent {
        private final InsightSource source;
        private final int originalAmount;
        private int newAmount;

        public Gain(Player player, int amount, InsightSource source) {
            super(player);
            this.originalAmount = amount;
            this.newAmount = amount;
            this.source = source;
        }

        public InsightSource getSource() {
            return source;
        }

        public int getOriginalAmount() {
            return originalAmount;
        }

        public int getNewAmount() {
            return newAmount;
        }

        public void setNewAmount(int newAmount) {
            this.newAmount = newAmount;
        }
    }
}
