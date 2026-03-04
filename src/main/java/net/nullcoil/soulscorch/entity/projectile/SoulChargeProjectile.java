package net.nullcoil.soulscorch.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.nullcoil.soulscorch.block.effect.ModEffects;
import net.nullcoil.soulscorch.entity.ModEntities;
import net.nullcoil.soulscorch.item.ModItems;

import java.util.List;

public class SoulChargeProjectile extends AbstractHurtingProjectile implements ItemSupplier {

    public SoulChargeProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public SoulChargeProjectile(Level level, LivingEntity shooter, Vec3 direction) {
        super(ModEntities.SOUL_CHARGE_PROJECTILE, shooter, direction, level);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ModItems.SOUL_CHARGE);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        // Ignore collisions with the Blazt that fired it
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity hitEntity = ((EntityHitResult) result).getEntity();
            if (this.ownedBy(hitEntity)) {
                return;
            }
        }

        if (!this.level().isClientSide()) {
            Difficulty difficulty = this.level().getDifficulty();

            // 1. Calculate Difficulty Multipliers
            float multiplier = switch (difficulty) {
                case PEACEFUL, EASY -> 0.33F;
                case NORMAL -> 0.66F;
                case HARD -> 1.0F;
            };

            int baseDuration = 600; // 30 seconds
            int scaledDuration = (int) (baseDuration * multiplier);
            float startRadius = 3.0F * multiplier;
            float maxRadius = 7.0F * multiplier;

            // 2. Spawn the Lingering Cloud
            AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            if (this.getOwner() instanceof LivingEntity livingOwner) {
                cloud.setOwner(livingOwner);
            }

            cloud.setCustomParticle(ParticleTypes.SOUL_FIRE_FLAME);
            cloud.setRadius(startRadius);
            cloud.setDuration(scaledDuration);
            // Calculate how much the radius should grow each tick to reach maxRadius by the end of its duration
            cloud.setRadiusPerTick((maxRadius - startRadius) / (float) cloud.getDuration());

            // 3. Apply Scaled Effects
            cloud.addEffect(new MobEffectInstance(ModEffects.SOULSCORCH, scaledDuration, 0, false, false, true));

            if (difficulty != Difficulty.PEACEFUL) {
                int damageAmp = switch (difficulty) {
                    case EASY -> 0; // Instant Damage I
                    case NORMAL -> 1; // Instant Damage II
                    case HARD -> 2; // Instant Damage III
                    default -> 0;
                };
                cloud.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 1, damageAmp, false, false, true));
            }

            // 4. Snap to Target Logic
            // Expands the search box to 4x2x4 blocks
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
            if (!list.isEmpty()) {
                for (LivingEntity livingEntity : list) {
                    double distance = this.distanceToSqr(livingEntity);
                    if (distance < 16.0D) { // Within 4 blocks
                        cloud.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                        break; // Snaps to the first valid entity it finds
                    }
                }
            }

            this.level().addFreshEntity(cloud);
            this.discard(); // Destroy the projectile
        }
    }

    // Swaps the default smoke trail for Soul Fire particles!
    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }
}