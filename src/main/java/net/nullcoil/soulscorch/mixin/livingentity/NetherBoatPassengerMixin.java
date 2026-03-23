package net.nullcoil.soulscorch.mixin.livingentity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.nullcoil.soulscorch.entity.vehicle.NetherBoat;
import net.nullcoil.soulscorch.entity.vehicle.NetherChestBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class NetherBoatPassengerMixin {
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void cancelLavaDamageInNetherBoat(ServerLevel serverLevel, DamageSource source, float f, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object) this;
        if (self.getVehicle() instanceof NetherBoat || self.getVehicle() instanceof NetherChestBoat) {
            if (self.getVehicle().isInLava() && source.is(DamageTypes.LAVA)) {
                cir.setReturnValue(false);
            }
        }
    }
}