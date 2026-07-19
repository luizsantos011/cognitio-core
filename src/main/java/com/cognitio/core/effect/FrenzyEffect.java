package com.cognitio.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FrenzyEffect extends MobEffect {
    public FrenzyEffect() {
        // Categoria prejudicial, cor roxa escuro
        super(MobEffectCategory.HARMFUL, 0xA020F0); 
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // Drena meio coração (1.0f) por segundo (como punição controlada)
        entity.hurt(entity.damageSources().magic(), 1.0f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // Aplica o efeito a cada 20 ticks (1 segundo)
        return duration > 0 && duration % 20 == 0;
    }
}
