package net.nullcoil.soulscorch.entity.vehicle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.nullcoil.soulscorch.particles.ModParticles;

import java.util.function.Supplier;

public class SeepingChestBoat extends NetherChestBoat {
    public SeepingChestBoat(EntityType<? extends NetherChestBoat> entityType, Level level, Supplier<Item> supplier) {
        super(entityType, level, supplier);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide() && this.random.nextInt(5) == 0) {
            Direction direction = Direction.getRandom(this.random);
            if (direction == Direction.UP) return;

            double x = this.getX() + (this.random.nextDouble() - 0.5) * this.getBbWidth();
            double y = this.getY() + this.random.nextDouble() * this.getBbHeight();
            double z = this.getZ() + (this.random.nextDouble() - 0.5) * this.getBbWidth();

            this.level().addParticle(ModParticles.SEEPING_DRIP_HANG, x, y, z, 0d, 0d, 0d);
            this.level().playLocalSound(this.blockPosition(), SoundEvents.BEEHIVE_DRIP,
                    SoundSource.NEUTRAL, 0.5F, 0.8F, false);
        }
    }
}