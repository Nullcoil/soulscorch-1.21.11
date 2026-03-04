package net.nullcoil.soulscorch;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.nullcoil.soulscorch.block.ModBlockEntities;
import net.nullcoil.soulscorch.block.ModBlocks;
import net.nullcoil.soulscorch.block.effect.ModEffects;
import net.nullcoil.soulscorch.entity.ModEntities;
import net.nullcoil.soulscorch.event.SleepHealthResetHandler;
import net.nullcoil.soulscorch.fire.FireRegistry;
import net.nullcoil.soulscorch.item.ModItems;
import net.nullcoil.soulscorch.sound.ModSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Soulscorch implements ModInitializer {
	public static final String MOD_ID = "soulscorch";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModEffects.register();
		SleepHealthResetHandler.register();
		FireRegistry.register();
		ModEntities.register();
		ModSounds.register();

		// 1. Group all Bastion chest types together
		List<ResourceKey<LootTable>> bastionTables = List.of(
				BuiltInLootTables.BASTION_TREASURE,
				BuiltInLootTables.BASTION_OTHER,
				BuiltInLootTables.BASTION_BRIDGE
		);

		// 2. Register a SINGLE modify event
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

			// 3. Check if the current loot table is any of the Bastion ones
			if (bastionTables.contains(key)) {

				// Inject the Enchantments
				tableBuilder.withPool(
						LootPool.lootPool().add(
								NestedLootTable.lootTableReference(
										ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MOD_ID, "inject/bastion_enchants"))
								)
						)
				);

				// Inject the Items
				tableBuilder.withPool(
						LootPool.lootPool().add(
								NestedLootTable.lootTableReference(
										ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MOD_ID, "inject/bastion_items"))
								)
						)
				);
			}
		});

		LOGGER.info("Soulscorch initialized successfully");
	}
}