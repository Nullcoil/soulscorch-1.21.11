package net.nullcoil.soulscorch.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.world.ModPlacedFeatures;

public class ModWorldGen {
    public static void generate() {
        Soulscorch.LOGGER.info("Registering Ghost Pepper worldgen feature...");
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.GHOST_PEPPER_PLACED_KEY
        );
        Soulscorch.LOGGER.info("Ghost Pepper feature registered.");
    }
}