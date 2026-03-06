package net.nullcoil.soulscorch.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jspecify.annotations.Nullable;

public class SoulRenderEffect extends InstantenousMobEffect {
    public SoulRenderEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Override
    public void applyInstantenousEffect(ServerLevel serverLevel, @Nullable Entity entity, @Nullable Entity entity2, LivingEntity livingEntity, int i, double d) {
        AttributeInstance maxHealthAttr = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        float currHealth = livingEntity.getHealth();

        if (maxHealthAttr != null) {
            float ratio = currHealth/(float)maxHealthAttr.getBaseValue();
            maxHealthAttr.setBaseValue(20d);
            livingEntity.setHealth(Mth.ceil(20*ratio));
        }
        super.applyInstantenousEffect(serverLevel, entity, entity2, livingEntity, i, d);
    }
}
