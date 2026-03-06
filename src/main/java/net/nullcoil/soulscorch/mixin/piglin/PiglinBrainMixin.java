package net.nullcoil.soulscorch.mixin.piglin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.nullcoil.soulscorch.util.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Target the MojMap class name
@Mixin(PiglinAi.class)
public class PiglinBrainMixin {

    @Inject(method = "isZombified", at = @At("HEAD"), cancellable = true)
    private static void isZombified(EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        // isIn() is now simply is()
        if (entityType.is(ModTags.Entities.PHOBIAS_OF_PIGLINS)) {
            cir.setReturnValue(true);
            // Note: cir.cancel() is technically redundant in Fabric mixins if you already called setReturnValue(),
            // but you can leave it if you prefer the visual clarity!
        }
    }
}