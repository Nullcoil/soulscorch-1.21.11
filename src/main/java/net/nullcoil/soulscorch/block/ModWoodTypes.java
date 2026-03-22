package net.nullcoil.soulscorch.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Map;

public class ModWoodTypes {
    private static final Map<String, WoodType> TYPES = new Object2ObjectArrayMap();
    public static final WoodType SEEPING_SALLOW = register(new WoodType("seeping_sallow", ModBlockSets.SEEPING_SALLOW));

    private static WoodType register(WoodType woodType) {
        TYPES.put(woodType.name(), woodType);
        return woodType;
    }
}
