package net.nullcoil.soulscorch.block;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.block.custom.*;
import net.nullcoil.soulscorch.particles.ModParticles;

import java.util.function.Function;

public class SeepingBlocks {
    public static final Block SEEPING_LEAVES = registerBlock("seeping_leaves",
            props -> new SeepingLeaves(0.02f, ModParticles.SEEPING_SALLOW_LEAVES, props), BlockBehaviour.Properties.of()
                    .strength(0.2f)
                    .randomTicks()
                    .sound(SoundType.LEAF_LITTER)
                    .noOcclusion()
                    .isValidSpawn(Blocks::ocelotOrParrot)
                    .isSuffocating(Blocks::never)
                    .isViewBlocking(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .isRedstoneConductor(Blocks::never));

    public static final Block SEEPING_LOG = registerBlock("seeping_log",
            SeepingLog::new, BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_SOIL.defaultMapColor())
                    .randomTicks()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_WOOD = registerBlock("seeping_wood",
            SeepingLog::new, BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_SOIL.defaultMapColor())
                    .randomTicks()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block STRIPPED_SEEPING_LOG = registerBlock("stripped_seeping_log",
            SeepingLog::new, BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_SOIL.defaultMapColor())
                    .randomTicks()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block STRIPPED_SEEPING_WOOD = registerBlock("stripped_seeping_wood",
            SeepingLog::new, BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_SOIL.defaultMapColor())
                    .randomTicks()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_PLANKS = registerBlock("seeping_planks",
            SeepingPlanks::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_BUTTON = registerBlock("seeping_button",
            properties -> new SeepingButton(ModBlockSets.SEEPING_SALLOW, 30, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_DOOR = registerBlock("seeping_door",
            properties -> new SeepingDoor(ModBlockSets.SEEPING_SALLOW, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_FENCE = registerBlock("seeping_fence",
            SeepingFence::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_FENCE_GATE = registerBlock("seeping_fence_gate",
            properties -> new SeepingFenceGate(ModWoodTypes.SEEPING_SALLOW, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_PRESSURE_PLATE = registerBlock("seeping_pressure_plate",
            properties -> new SeepingPressurePlate(ModBlockSets.SEEPING_SALLOW, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_SLAB = registerBlock("seeping_slab",
            SeepingSlab::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_STAIRS = registerBlock("seeping_stairs",
            properties -> new SeepingStairs(SEEPING_PLANKS.defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(SEEPING_PLANKS)
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .strength(2f)
                    .sound(SoundType.WOOD));

    public static final Block SEEPING_TRAPDOOR = registerBlock("seeping_trapdoor",
            properties -> new SeepingTrapdoor(ModBlockSets.SEEPING_SALLOW, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .randomTicks()
                    .strength(2f)
                    .sound(SoundType.WOOD));

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        return registerBlock(name,factory,properties, true);
    }

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, boolean buildItem) {
        Identifier id = Identifier.tryBuild(Soulscorch.MOD_ID, name);

        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);

        Block block = factory.apply(properties.setId(blockKey));
        if(buildItem) {
            Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block, new Item.Properties().setId(itemKey)));
        }

        // Register and return the Block
        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    public static void register() {
        Soulscorch.LOGGER.info("Registering Seeping Blocks for " + Soulscorch.MOD_ID);
    }
}
