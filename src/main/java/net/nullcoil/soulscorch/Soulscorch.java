package net.nullcoil.soulscorch;

import net.fabricmc.api.ModInitializer;

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

		LOGGER.info("Soulscorch initialized successfully");
	}
}