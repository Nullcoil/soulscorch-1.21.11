package net.nullcoil.soulscorch;

import net.fabricmc.api.ModInitializer;
import net.nullcoil.soulscorch.alchemy.ModAlchemy;
import net.nullcoil.soulscorch.block.ModBlockEntities;
import net.nullcoil.soulscorch.block.ModBlocks;
import net.nullcoil.soulscorch.effect.ModEffects;
import net.nullcoil.soulscorch.entity.ModEntities;
import net.nullcoil.soulscorch.event.ModEvents;
import net.nullcoil.soulscorch.fire.ModFire;
import net.nullcoil.soulscorch.item.ModItems;
import net.nullcoil.soulscorch.loot.ModLootTables;
import net.nullcoil.soulscorch.sound.ModSounds;
import net.nullcoil.soulscorch.world.gen.ModEntitySpawns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Soulscorch implements ModInitializer {
	public static final String MOD_ID = "soulscorch";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModEffects.register();
		ModEvents.register();
		ModFire.register();
		ModEntities.register();
		ModSounds.register();
		ModLootTables.register();
		ModEntitySpawns.register();
		ModAlchemy.register();

		LOGGER.info("Soulscorch initialized successfully");
	}
}