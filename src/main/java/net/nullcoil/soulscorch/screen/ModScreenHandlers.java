package net.nullcoil.soulscorch.screen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.nullcoil.soulscorch.Soulscorch;

public class ModScreenHandlers {
    public static final MenuType<SoulBrewingStandScreenHandler> SOUL_BREWING_STAND =
            Registry.register(
                    net.minecraft.core.registries.BuiltInRegistries.MENU,
                    Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "soul_brewing_stand_screen_handler"),
                    new MenuType<>(SoulBrewingStandScreenHandler::new, FeatureFlags.VANILLA_SET));

    public static void register() {
        Soulscorch.LOGGER.info("Registering Mod Screen Handlers for " + Soulscorch.MOD_ID);
    }
}