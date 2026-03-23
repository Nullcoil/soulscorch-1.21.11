package net.nullcoil.soulscorch.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.nullcoil.soulscorch.Soulscorch;

import java.lang.reflect.Method;
import java.util.Map;

public class ModWoodTypes {
    private static final Map<String, WoodType> TYPES = new Object2ObjectArrayMap();
    public static final WoodType SEEPING_SALLOW = WoodType.register(new WoodType("seeping_sallow", ModBlockSets.SEEPING_SALLOW));

    public static void register() {
        Soulscorch.LOGGER.info("Registering Wood Types for " + Soulscorch.MOD_ID);
    }
}
