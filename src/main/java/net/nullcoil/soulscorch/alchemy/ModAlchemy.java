package net.nullcoil.soulscorch.alchemy;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.effect.ModEffects;
import net.nullcoil.soulscorch.item.ModItems;

public class ModAlchemy {
    public static final Holder<Potion> SOUL_RENDERING;

    private static Holder<Potion> register(String path, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, path), potion);
    }

    static {
        SOUL_RENDERING = register("soul_rendering",
                new Potion(
                        "soul_rendering",
                        new MobEffectInstance[]{
                                new MobEffectInstance(ModEffects.SOUL_RENDER, 1)
                        }));
    }

    public static void register() {
        Soulscorch.LOGGER.info("Registering Alchemy for " + Soulscorch.MOD_ID);

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    Potions.AWKWARD,
                    Ingredient.of(ModItems.SOUL_CREAM),
                    SOUL_RENDERING
            );
        });
    }
}
