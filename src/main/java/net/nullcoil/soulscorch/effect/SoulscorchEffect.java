package net.nullcoil.soulscorch.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.nullcoil.soulscorch.item.ModItems;

public class SoulscorchEffect extends MobEffect {
    protected SoulscorchEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Override
    public void onMobHurt(ServerLevel serverLevel, LivingEntity livingEntity, int i, DamageSource damageSource, float f) {
        if (f <= 0.0f || (livingEntity.hasEffect(ModEffects.CAT_BUFF) || livingEntity.hasEffect(ModEffects.DOG_BUFF))) {
            super.onMobHurt(serverLevel, livingEntity, i, damageSource, f);
            return;
        }

        if (livingEntity instanceof Player player) {
            boolean totemFound = false;
            for (InteractionHand hand : InteractionHand.values()) {
                ItemStack stack = player.getItemInHand(hand);

                if (stack.is(ModItems.SOULWARD_TOTEM)) {
                    EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                    stack.hurtAndBreak(5, player, slot);
                    totemFound = true;
                    break;
                }
            }

            if (!totemFound) {
                AttributeInstance maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);

                if (maxHealthAttr != null) {
                    double currentMax = maxHealthAttr.getBaseValue();
                    double newMax = Math.max(1.0, currentMax - 1.0);

                    if (newMax < currentMax) {
                        maxHealthAttr.setBaseValue(newMax);
                        if (player.getHealth() > (float) newMax) {
                            player.setHealth((float) newMax);
                        }
                    }
                }
            }
        }

        super.onMobHurt(serverLevel, livingEntity, i, damageSource, f);
    }
}
