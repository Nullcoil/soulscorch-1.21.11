package net.nullcoil.soulscorch.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.food.FoodData;
import net.nullcoil.soulscorch.attribute.ModAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    // Target the two serverPlayer.isHurt() checks inside the FoodData tick loop
    @WrapOperation(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isHurt()Z")
    )
    private boolean soulscorch$stopHealingPastCorruption(ServerPlayer player, Operation<Boolean> original) {
        // Find out what vanilla thinks first
        boolean vanillaThinksWeAreHurt = original.call(player);

        if (vanillaThinksWeAreHurt) {
            AttributeInstance corruptionAttr = player.getAttribute(ModAttributes.CORRUPTION);

            if (corruptionAttr != null && corruptionAttr.getValue() > 0) {
                float safeHealth = player.getMaxHealth() - (float) corruptionAttr.getValue();

                // If our health is sitting exactly at our safe limit, we are "full"
                if (player.getHealth() >= safeHealth) {
                    return false; // Trick the stomach into thinking we are completely unhurt!
                }
            }
        }

        // Otherwise, return the normal vanilla answer
        return vanillaThinksWeAreHurt;
    }
}