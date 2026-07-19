package com.cognitio.core.registry;

import com.cognitio.core.CognitioCore;
import com.cognitio.core.effect.FrenzyEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, CognitioCore.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> FRENZY = MOB_EFFECTS.register("frenzy", FrenzyEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
