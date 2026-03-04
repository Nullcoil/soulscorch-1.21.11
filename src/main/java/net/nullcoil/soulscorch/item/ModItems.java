package net.nullcoil.soulscorch.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.block.ModBlocks;
import net.nullcoil.soulscorch.entity.ModEntities;

import java.util.function.Function;

public class ModItems {
    public static final Item BLAZT_POWDER = registerItem(
            "blazt_powder", Item::new, new Item.Properties() // You can add .stacksTo(64) or .fireResistant() here if needed!
    );
    public static final Item BLAZT_ROD = registerItem(
            "blazt_rod", Item::new, new Item.Properties()
    );
    public static final Item SOUL_CREAM = registerItem(
            "soul_cream", Item::new, new Item.Properties()
    );
    public static final Item SOUL_CHARGE = registerItem(
            "soul_charge", FireChargeItem::new, new Item.Properties()
    );
    public static final Item SOUL_SHARD = registerItem(
            "soul_shard", Item::new, new Item.Properties()
    );
    public static final Item SOULWARD_TOTEM = registerItem(
            "soulward_totem", SoulwardTotemItem::new, new Item.Properties()
    );

    public static final Item SOULLESS_SPAWN_EGG = registerItem(
            "soulless_spawn_egg",
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(ModEntities.SOULLESS)
    );

    public static final Item BLAZT_SPAWN_EGG = registerItem(
            "blazt_spawn_egg",
            SpawnEggItem::new, new Item.Properties().spawnEgg(ModEntities.BLAZT)
    );


    // 2. The completed helper method
    private static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties properties) {
        // Build the Identifier
        Identifier id = Identifier.tryBuild(Soulscorch.MOD_ID, name);

        // Create the ResourceKey
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);

        // Inject the ID into the properties, then create the item using the factory
        Item item = factory.apply(properties.setId(itemKey));
        return Registry.register(BuiltInRegistries.ITEM, itemKey, item);
    }

    // Call this in your Soulscorch.java onInitialize() method!
    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.accept(BLAZT_POWDER);
            entries.accept(BLAZT_ROD);
            entries.accept(SOUL_CREAM);
            entries.accept(SOUL_CHARGE);
            entries.accept(SOUL_SHARD);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(SOULWARD_TOTEM);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.accept(ModBlocks.SOUL_BREWING_STAND);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
            entries.accept(ModBlocks.IRON_BULB_BLOCK);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.accept(ModBlocks.CERULEAN_FROGLIGHT);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.accept(SOULLESS_SPAWN_EGG);
            entries.accept(BLAZT_SPAWN_EGG);
        });

        Soulscorch.LOGGER.info("Registering Mod Items for " + Soulscorch.MOD_ID);
    }
}