package net.nullcoil.soulscorch.mixin.livingentity;

import net.minecraft.world.entity.Entity;
import net.nullcoil.soulscorch.entity.vehicle.NetherBoat;
import net.nullcoil.soulscorch.entity.vehicle.NetherChestBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class NetherBoatPassengerLavaIgniteMixin {
    @Inject(method = "lavaIgnite", at = @At("HEAD"), cancellable = true)
    private void preventLavaIgniteInNetherBoat(CallbackInfo ci) {
        Entity self = (Entity)(Object) this;
        if (self.isPassenger()) {
            Entity vehicle = self.getVehicle();
            if ((vehicle instanceof NetherBoat || vehicle instanceof NetherChestBoat) && vehicle.isInLava()) {
                ci.cancel();
            }
        }
    }
}