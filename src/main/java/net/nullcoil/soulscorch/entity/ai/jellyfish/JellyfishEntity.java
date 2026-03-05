package net.nullcoil.soulscorch.entity.ai.jellyfish;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.nullcoil.soulscorch.effect.ModEffects;
import net.nullcoil.soulscorch.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class JellyfishEntity extends Monster {
    public final AnimationState IDLE = new AnimationState();

    public JellyfishEntity(EntityType<? extends JellyfishEntity> type, Level level) {
        super(type, level);
        this.moveControl = new JellyfishMoveControl(this);
        this.xpReward = 2; // experiencePoints mapping
    }

    @Override protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    @Override public boolean causeFallDamage(double d, float f, DamageSource damageSource) { return false; }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, spawnData);

        float yaw = this.random.nextFloat() * 360f - 180f;
        this.setYRot(yaw);
        this.setYHeadRot(yaw);
        this.setYBodyRot(yaw);
        this.setBaby(false);

        return data;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0d)
                .add(Attributes.MOVEMENT_SPEED, 0.025d)
                .add(Attributes.ATTACK_DAMAGE, 2d)
                .add(Attributes.GRAVITY, 0d);
    }

    @Override
    public void registerGoals() { // initGoals mapping
        this.goalSelector.addGoal(0, new JellyfishEntity.FlyRandomlyGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.IDLE.startIfStopped(this.tickCount);
        }

        if (!this.level().isClientSide()) {
            // Replaced getOtherEntities with getEntities
            List<Entity> entities = this.level().getEntities(this, this.getBoundingBox(),
                    e -> e instanceof LivingEntity &&
                            !e.getType().is(ModTags.Entities.SOULSCORCH_ENTITIES) &&
                            !(e instanceof Frog));

            for (Entity e : entities) {
                LivingEntity living = (LivingEntity) e;
                double dx = living.getX() - this.getX();
                double dz = living.getZ() - this.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);

                if (dist != 0) {
                    dx /= dist;
                    dz /= dist;

                    if (living instanceof Player p && (p.isCreative() || p.isSpectator())) continue;

                    living.hurtServer(
                            (ServerLevel) this.level(),
                            this.damageSources().mobAttack(this),
                            (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                    );

                    // Ensure CAT_BUFF exists in your mapped ModEffects!
                    if (!living.hasEffect(ModEffects.CAT_BUFF) || !living.hasEffect(ModEffects.DOG_BUFF)) {
                        living.addEffect(new MobEffectInstance(
                                ModEffects.SOULSCORCH,
                                600,
                                0,
                                false,
                                false,
                                true
                        ));
                    }
                }
            }
        }
    }

    public static class JellyfishMoveControl extends MoveControl {
        private static final double REACHED_DESTINATION_DISTANCE_SQUARED = 1.0;
        private static final double HORIZONTAL_DAMPING = 0.98;
        private static final double VERTICAL_DAMPING = 0.95;
        private boolean hasActiveTarget = false;
        private final JellyfishEntity jelly;

        public JellyfishMoveControl(JellyfishEntity jelly) {
            super(jelly);
            this.jelly = jelly;
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) { // isMoving mapping
                double dx = this.wantedX - this.mob.getX();
                double dy = this.wantedY - this.mob.getY();
                double dz = this.wantedZ - this.mob.getZ();

                double distSq = dx * dx + dy * dy + dz * dz;

                if (distSq < REACHED_DESTINATION_DISTANCE_SQUARED) {
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale(0.5));
                    this.operation = MoveControl.Operation.WAIT;
                    this.hasActiveTarget = false;
                    return;
                }

                double dist = Math.sqrt(distSq);
                dx /= dist; dy /= dist; dz /= dist;

                double speed = this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);

                this.mob.setDeltaMovement(
                        this.mob.getDeltaMovement().add(
                                dx * speed * 0.1,
                                dy * speed * 0.1,
                                dz * speed * 0.1
                        )
                );

                // Keep orientation constant
                this.mob.setYRot(this.mob.getYRot());
                this.mob.setXRot(this.mob.getXRot());
            } else {
                // idle drifting slowdown
                this.mob.setDeltaMovement(
                        this.mob.getDeltaMovement().multiply(HORIZONTAL_DAMPING, VERTICAL_DAMPING, HORIZONTAL_DAMPING)
                );
            }
        }

        public boolean hasActiveTarget() {
            return this.hasActiveTarget;
        }
    }

    @Override
    public boolean fireImmune() { return true; }

    static class FlyRandomlyGoal extends Goal {
        private final JellyfishEntity jelly;

        public FlyRandomlyGoal(JellyfishEntity jelly) {
            this.jelly = jelly;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl moveControl = this.jelly.getMoveControl();
            if (!moveControl.hasWanted()) return true;

            double dx = moveControl.getWantedX() - this.jelly.getX();
            double dy = moveControl.getWantedY() - this.jelly.getY();
            double dz = moveControl.getWantedZ() - this.jelly.getZ();
            double distSq = dx * dx + dy * dy + dz * dz;

            return distSq < 1.0 || distSq > 3600.0;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            RandomSource random = this.jelly.getRandom();
            Level level = this.jelly.level();

            for (int i = 0; i < 16; i++) {
                double targetX = this.jelly.getX() + (random.nextDouble() * 2.0 - 1.0) * 16.0;
                double targetY = this.jelly.getY() + (random.nextDouble() * 2.0 - 1.0) * 16.0;
                double targetZ = this.jelly.getZ() + (random.nextDouble() * 2.0 - 1.0) * 16.0;

                BlockPos targetPos = BlockPos.containing(targetX, targetY, targetZ);

                if (!isAreaClear(level, targetPos)) continue;

                Vec3 currentPos = this.jelly.position();
                Vec3 targetVec = new Vec3(targetX, targetY, targetZ);

                // Raycast mapping
                HitResult hit = level.clip(
                        new ClipContext(
                                currentPos, targetVec,
                                ClipContext.Block.COLLIDER,
                                ClipContext.Fluid.NONE,
                                this.jelly
                        )
                );

                if (hit.getType() == HitResult.Type.MISS) {
                    this.jelly.getMoveControl().setWantedPosition(targetX, targetY, targetZ, 1.0);
                    return;
                }
            }
        }

        private boolean isAreaClear(Level level, BlockPos pos) {
            return level.isEmptyBlock(pos)
                    && level.isEmptyBlock(pos.above())
                    && level.isEmptyBlock(pos.below())
                    && level.isEmptyBlock(pos.north())
                    && level.isEmptyBlock(pos.south())
                    && level.isEmptyBlock(pos.east())
                    && level.isEmptyBlock(pos.west());
        }
    }

    // Spawn validation maps to checkSpawnObstruction using LevelReader
    public boolean checkSpawnObstruction(LevelReader level) {
        return canSpawnOnGround(level) || canSpawnInAir(level);
    }

    private boolean canSpawnOnGround(LevelReader level) {
        BlockPos pos = this.blockPosition();
        BlockPos groundPos = pos.below();

        return level.getBlockState(groundPos).isSolid()
                && level.isEmptyBlock(pos)
                && level.getMaxLocalRawBrightness(pos) <= 11
                && !isTooDense(level, pos)
                && !level.getBlockState(groundPos).is(Blocks.BEDROCK);
    }

    private boolean canSpawnInAir(LevelReader level) {
        BlockPos pos = this.blockPosition();

        if (!level.isEmptyBlock(pos)) {
            return false;
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos checkPos = pos.offset(x, y, z); // pos.add mapping
                    if (!level.isEmptyBlock(checkPos)) {
                        return false;
                    }
                }
            }
        }

        return level.getMaxLocalRawBrightness(pos) <= 11
                && !isTooDense(level, pos)
                && this.blockPosition().getY() < 125;
    }

    private boolean isTooDense(LevelReader level, BlockPos pos) {
        List<JellyfishEntity> nearbyJellyfish = this.level().getEntitiesOfClass(
                JellyfishEntity.class,
                this.getBoundingBox().inflate(16) // expand mapping
        );

        return nearbyJellyfish.size() > 3;
    }

    // Modernized static spawn check for your SpawnPlacements Registry
    public static boolean checkJellyfishSpawnRules(EntityType<? extends JellyfishEntity> type, ServerLevelAccessor level,
                                                   EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        if (spawnReason == EntitySpawnReason.NATURAL) {
            // Using type.create() is safer than manually instantiating in worldgen
            JellyfishEntity dummy = type.create(level.getLevel(), spawnReason);
            if (dummy != null) {
                dummy.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                return dummy.checkSpawnObstruction(level);
            }
            return false;
        }
        return true;
    }
}