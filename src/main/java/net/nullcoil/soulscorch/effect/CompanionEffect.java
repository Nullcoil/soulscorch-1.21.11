package net.nullcoil.soulscorch.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CompanionEffect extends MobEffect {
    protected CompanionEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    protected CompanionEffect(MobEffectCategory mobEffectCategory, int i, ParticleOptions particleOptions) {
        super(mobEffectCategory, i, particleOptions);
    }
}
