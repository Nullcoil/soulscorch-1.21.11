package net.nullcoil.soulscorch.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.block.entity.SoulBrewingStandBlockEntity;

public class ModBlockEntities {

    public static final BlockEntityType<SoulBrewingStandBlockEntity> SOUL_BREWING_STAND =
            Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    Identifier.tryBuild(Soulscorch.MOD_ID, "soul_brewing_stand"),
                    FabricBlockEntityTypeBuilder.create(SoulBrewingStandBlockEntity::new, ModBlocks.SOUL_BREWING_STAND).build(null)
            );

    // Call this in your main mod initializer after calling registerModBlocks()
    public static void register() {
        Soulscorch.LOGGER.info("Registering Mod Block Entities for " + Soulscorch.MOD_ID);
    }
}