package net.nullcoil.soulscorch.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.nullcoil.soulscorch.Soulscorch;

import java.util.Map;

public class ModBlockSets {
    private static final Map<String, BlockSetType> TYPES = new Object2ObjectArrayMap();

    public static final BlockSetType SEEPING_SALLOW = register(new BlockSetType("seeping_sallow"));

    private static BlockSetType register(BlockSetType type) {
        TYPES.put(type.name(), type);
        return type;
    }

    public static void register() {
        Soulscorch.LOGGER.info("Registering Block Sets for " + Soulscorch.MOD_ID);
    }
}
