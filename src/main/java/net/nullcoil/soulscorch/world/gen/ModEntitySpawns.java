package net.nullcoil.soulscorch.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.entity.ModEntities;

public class ModEntitySpawns {
    public static void register() {
        Soulscorch.LOGGER.info("Adding Mod Entity Spawns for " + Soulscorch.MOD_ID);

        // =========================================
        // 1. Biome Spawn Rates (Where they spawn)
        // =========================================

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                MobCategory.MONSTER, ModEntities.BLAZT, 8, 1, 1);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                MobCategory.MONSTER, ModEntities.SOULLESS, 100, 2, 8);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                MobCategory.MONSTER, ModEntities.RESTLESS, 20, 1, 1);


        // =========================================
        // 2. Global Spawn Rules (How they spawn)
        // =========================================

        SpawnPlacements.register(ModEntities.BLAZT, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);

        SpawnPlacements.register(ModEntities.SOULLESS, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, Mob::checkMobSpawnRules);

        SpawnPlacements.register(ModEntities.RESTLESS, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, Mob::checkMobSpawnRules);
    }
}