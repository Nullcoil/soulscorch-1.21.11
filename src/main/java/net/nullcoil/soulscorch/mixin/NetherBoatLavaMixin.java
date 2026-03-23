package net.nullcoil.soulscorch.mixin;

import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.nullcoil.soulscorch.entity.vehicle.NetherBoat;
import net.nullcoil.soulscorch.entity.vehicle.NetherChestBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractBoat.class)
public class NetherBoatLavaMixin {
    @Redirect(
            method = "checkInWater()Z",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean treatLavaAsWaterCheckInWater(FluidState fluidState, TagKey<Fluid> tag) {
        Object self = this;
        if (tag == FluidTags.WATER && (self instanceof NetherBoat || self instanceof NetherChestBoat)) {
            return fluidState.is(FluidTags.WATER) || fluidState.is(FluidTags.LAVA);
        }
        return fluidState.is(tag);
    }

    @Redirect(
            method = "isUnderwater()Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat$Status;",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean treatLavaAsWaterIsUnderwater(FluidState fluidState, TagKey<Fluid> tag) {
        Object self = this;
        if (tag == FluidTags.WATER && (self instanceof NetherBoat || self instanceof NetherChestBoat)) {
            return fluidState.is(FluidTags.WATER) || fluidState.is(FluidTags.LAVA);
        }
        return fluidState.is(tag);
    }
}