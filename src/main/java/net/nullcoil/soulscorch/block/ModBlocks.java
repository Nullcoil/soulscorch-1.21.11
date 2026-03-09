package net.nullcoil.soulscorch.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.block.custom.GhostPepperShrubBlock;
import net.nullcoil.soulscorch.block.custom.IronBulbBlock;
import net.nullcoil.soulscorch.block.custom.SoulBrewingStandBlock;
import net.nullcoil.soulscorch.block.custom.SoulSlagBlock;

import java.util.function.Function;

public class ModBlocks {

    // 1. Declare the block, passing the constructor as a method reference (SoulBrewingStandBlock::new)
    public static final Block SOUL_BREWING_STAND = registerBlock(
            "soul_brewing_stand",
            SoulBrewingStandBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(0.5F)
                    .lightLevel(state -> 1)
                    .noOcclusion()
    );

    public static final Block IRON_BULB_BLOCK = registerBlock(
            "iron_bulb",
            IronBulbBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(Blocks.IRON_BLOCK.defaultMapColor())
                    .strength(3f,6f)
                    .sound(SoundType.COPPER_BULB)
                    .requiresCorrectToolForDrops()
                    .isRedstoneConductor(Blocks::never)
                    .lightLevel(Blocks.litBlockEmission(10))
    );

    public static final Block CERULEAN_FROGLIGHT = registerBlock(
            "cerulean_froglight",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_FIRE.defaultMapColor())
                    .strength(0.3f)
                    .lightLevel(blockStatex -> 15)
                    .sound(SoundType.FROGLIGHT)
    );

    public static final Block SOUL_SLAG_BLOCK = registerBlock(
            "soul_slag",
            SoulSlagBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_FIRE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 2)
                    .strength(.5f)
                    .isValidSpawn((state, getter, pos, type)-> type.fireImmune())
                    .hasPostProcess(Blocks::always)
    );

    public static final Block GHOST_PEPPER_SHRUB = registerBlock("ghost_pepper_shrub",
            GhostPepperShrubBlock::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .noCollision() // Lets players walk through it
                    .instabreak()   // Breaks instantly when punched
                    .randomTicks()  // CRUCIAL: Tells the game to grow it over time
                    .sound(SoundType.SWEET_BERRY_BUSH)
                    .pushReaction(PushReaction.DESTROY), // Pistons will break it
            false
            );

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        return registerBlock(name,factory,properties, true);
    }

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, boolean buildItem) {
        Identifier id = Identifier.tryBuild(Soulscorch.MOD_ID, name);

        // Create the ResourceKeys for both the Block and the Item
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);

        // Instantiate the block, injecting the ID into the properties first to prevent the NPE
        Block block = factory.apply(properties.setId(blockKey));

        // Register the BlockItem (Items also need their IDs set in their properties now)
        if(buildItem) {
            Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block, new Item.Properties().setId(itemKey)));
        }

        // Register and return the Block
        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    public static void register() {
        Soulscorch.LOGGER.info("Registering Mod Blocks for " + Soulscorch.MOD_ID);
    }
}