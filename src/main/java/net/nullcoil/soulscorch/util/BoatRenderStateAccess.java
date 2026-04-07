package net.nullcoil.soulscorch.util;

public interface BoatRenderStateAccess {
    boolean soulscorch$isNetherBoat();
    void soulscorch$setIsNetherBoat(boolean value);

    // New lava flag
    boolean soulscorch$isInLava();
    void soulscorch$setIsInLava(boolean value);

}