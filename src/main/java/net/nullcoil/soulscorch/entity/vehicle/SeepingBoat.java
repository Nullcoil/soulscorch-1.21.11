package net.nullcoil.soulscorch.entity.vehicle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nullcoil.soulscorch.particles.ModParticles;

import java.util.function.Supplier;

public class SeepingBoat extends NetherBoat {
    public SeepingBoat(EntityType<? extends NetherBoat> entityType, Level level, Supplier<Item> supplier) {
        super(entityType, level, supplier);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide() && this.random.nextInt(5) == 0) {
            spawnDripParticle(this.level(), this.blockPosition(), this.random);
        }
    }

    private void spawnDripParticle(Level level, BlockPos pos, RandomSource random) {
        Direction direction = Direction.getRandom(random);
        if (direction == Direction.UP) return;

        double x = this.getX() + (random.nextDouble() - 0.5) * this.getBbWidth();
        double y = this.getY() + random.nextDouble() * this.getBbHeight();
        double z = this.getZ() + (random.nextDouble() - 0.5) * this.getBbWidth();

        level.addParticle(ModParticles.SEEPING_DRIP_HANG, x, y, z, 0d, 0d, 0d);
        level.playLocalSound(pos, SoundEvents.BEEHIVE_DRIP,
                SoundSource.NEUTRAL, 0.5F, 0.8F, false);
    }
}