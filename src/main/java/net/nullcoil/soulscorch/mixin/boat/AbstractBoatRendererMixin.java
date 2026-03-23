package net.nullcoil.soulscorch.mixin.boat;

import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.nullcoil.soulscorch.entity.vehicle.NetherBoat;
import net.nullcoil.soulscorch.entity.vehicle.NetherChestBoat;
import net.nullcoil.soulscorch.util.BoatRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoatRenderer.class)
public class AbstractBoatRendererMixin {
    @Inject(method = "extractRenderState*", at = @At("TAIL"))
    private void setNetherBoatFlag(AbstractBoat boat, BoatRenderState state, float f, CallbackInfo ci) {
        BoatRenderStateAccess access = (BoatRenderStateAccess)(Object) state;
        access.soulscorch$setIsNetherBoat(boat instanceof NetherBoat || boat instanceof NetherChestBoat);
        access.soulscorch$setIsInLava(boat.isInLava());   // new
    }
}