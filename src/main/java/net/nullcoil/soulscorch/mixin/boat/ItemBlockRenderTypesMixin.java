package net.nullcoil.soulscorch.mixin.boat;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin {

    @Inject(
            method = "getRenderLayer(Lnet/minecraft/world/level/material/FluidState;)Lnet/minecraft/client/renderer/chunk/ChunkSectionLayer;",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void redirectLavaToTranslucent(FluidState fluidState,
                                                  CallbackInfoReturnable<ChunkSectionLayer> cir) {
        if (fluidState.is(FluidTags.LAVA)) {
            cir.setReturnValue(ChunkSectionLayer.TRANSLUCENT);
        }
    }
}