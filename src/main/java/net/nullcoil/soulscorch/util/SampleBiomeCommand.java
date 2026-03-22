package net.nullcoil.soulscorch.util;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.RandomState;

public class SampleBiomeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("samplebiome")
                .executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();
                    ServerLevel level = source.getLevel();
                    BlockPos pos = BlockPos.containing(source.getPosition());

                    if (!(level.getChunkSource().getGenerator() instanceof NoiseBasedChunkGenerator generator)) {
                        source.sendFailure(Component.literal("Only works in noise-generated dimensions."));
                        return 0;
                    }

                    RandomState randomState = level.getChunkSource().randomState();
                    NoiseRouter router = randomState.router();

                    DensityFunction.SinglePointContext ctx2 = new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ());

                    double temperature      = router.temperature().compute(ctx2);
                    double humidity        = router.vegetation().compute(ctx2);
                    double continentalness = router.continents().compute(ctx2);
                    double erosion         = router.erosion().compute(ctx2);
                    double depth           = router.depth().compute(ctx2);
                    double weirdness       = router.ridges().compute(ctx2);

                    Identifier biomeKey = level.getBiome(pos)
                            .unwrapKey()
                            .map(ResourceKey::identifier)
                            .orElse(Identifier.withDefaultNamespace("unknown"));

                    source.sendSuccess(() -> Component.literal(
                            "=== Biome Sample @ " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " ===\n" +
                                    "Biome:           " + biomeKey + "\n" +
                                    "Temperature:     " + String.format("%.4f", temperature) + "\n" +
                                    "Humidity:        " + String.format("%.4f", humidity) + "\n" +
                                    "Continentalness: " + String.format("%.4f", continentalness) + "\n" +
                                    "Erosion:         " + String.format("%.4f", erosion) + "\n" +
                                    "Depth:           " + String.format("%.4f", depth) + "\n" +
                                    "Weirdness:       " + String.format("%.4f", weirdness)
                    ), false);

                    return 1;
                })
        );
    }
}