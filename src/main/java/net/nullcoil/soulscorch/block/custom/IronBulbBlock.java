package net.nullcoil.soulscorch.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CopperBulbBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;

public class IronBulbBlock extends CopperBulbBlock {

    public IronBulbBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl) {
        if (level instanceof ServerLevel serverLevel) {
            // Check if our powered state is out of sync with the redstone signal
            boolean hasSignal = serverLevel.hasNeighborSignal(blockPos);
            if (hasSignal != blockState.getValue(POWERED)) {

                // Instead of flipping instantly, we schedule a tick for 1 game tick in the future.
                // We also check if a tick is already scheduled so we don't spam the tick schedule.
                if (!serverLevel.getBlockTicks().hasScheduledTick(blockPos, this)) {
                    serverLevel.scheduleTick(blockPos, this, 2);
                }
            }
        }
    }

    @Override
    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState2.getBlock() != blockState.getBlock() && level instanceof ServerLevel serverLevel) {
            boolean hasSignal = serverLevel.hasNeighborSignal(blockPos);
            if (hasSignal != blockState.getValue(POWERED)) {
                if (!serverLevel.getBlockTicks().hasScheduledTick(blockPos, this)) {
                    serverLevel.scheduleTick(blockPos, this, 2);
                }
            }
        }
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        // When our 1-tick timer goes off, NOW we actually check the power and flip the light
        this.checkAndFlip(blockState, serverLevel, blockPos);
    }
}