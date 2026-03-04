package net.nullcoil.soulscorch.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.nullcoil.soulscorch.Soulscorch;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SOULBASED_BLOCKS = createTag("soulstuff");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SOULBASED_ITEMS = createTag("soulstuff");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, name));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> SOULSCORCH_ENTITIES = createTag("soulscorch_entities");
        public static final TagKey<EntityType<?>> PHOBIAS_OF_PIGLINS = createTag("phobias_of_piglins");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> SOUL_BIOMES = createTag("soul_biomes");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, name));
        }
    }
}