package net.nullcoil.soulscorch.mixin.boat;

import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.nullcoil.soulscorch.util.BoatRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BoatRenderState.class)
public class BoatRenderStateMixin implements BoatRenderStateAccess {
    @Unique
    private boolean soulscorch$isNetherBoat = false;
    @Unique
    private boolean soulscorch$isInLava = false;      // new

    @Override
    public boolean soulscorch$isNetherBoat() {
        return this.soulscorch$isNetherBoat;
    }

    @Override
    public void soulscorch$setIsNetherBoat(boolean value) {
        this.soulscorch$isNetherBoat = value;
    }

    @Override
    public boolean soulscorch$isInLava() {
        return this.soulscorch$isInLava;
    }

    @Override
    public void soulscorch$setIsInLava(boolean value) {
        this.soulscorch$isInLava = value;
    }
}