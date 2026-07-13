package com.cognitio.core.influence;

import com.cognitio.api.influence.InfluenceEvent;

import net.neoforged.bus.api.SubscribeEvent;

public class InfluenceMentalHandler {

    @SubscribeEvent
    public void onPsychicInfluence(InfluenceEvent.Psychic event) {
        net.minecraft.world.entity.player.Player player = event.getPlayer();
        if (!(player.level() instanceof net.minecraft.server.level.ServerLevel serverLevel)) return;

        int perception = event.getCurrentPerception();
        double random = player.getRandom().nextDouble();

        if (perception >= 75) {
            if (random < 0.05) {
                serverLevel.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD.value(), net.minecraft.sounds.SoundSource.AMBIENT, 0.5f, 0.8f);
            }
        } else if (perception >= 50) {
            if (random < 0.10) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SQUID_INK, player.getX(), player.getY() + 1.0, player.getZ(), 5, 0.5, 0.5, 0.5, 0.01);
            }
        } else if (perception >= 25) {
            if (random < 0.02) {
                serverLevel.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.AMBIENT_CAVE.value(), net.minecraft.sounds.SoundSource.AMBIENT, 0.3f, 1.0f);
            }
        }
    }
}