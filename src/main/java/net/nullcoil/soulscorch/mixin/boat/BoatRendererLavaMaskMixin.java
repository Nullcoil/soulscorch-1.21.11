package net.nullcoil.soulscorch.mixin.boat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Unit;
import net.nullcoil.soulscorch.util.BoatRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatRenderer.class)
public class BoatRendererLavaMaskMixin {
    @Unique
    private Model.Simple soulscorch$lavaPatchModel;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initLavaPatchModel(EntityRendererProvider.Context context,
                                    ModelLayerLocation layer,
                                    CallbackInfo ci) {
        this.soulscorch$lavaPatchModel = new Model.Simple(
                context.bakeLayer(ModelLayers.BOAT_WATER_PATCH),
                (identifier) -> RenderTypes.waterMask()
        );
    }

    @Inject(method = "submitTypeAdditions", at = @At("TAIL"))
    private void submitLavaMask(BoatRenderState state, PoseStack poseStack,
                                SubmitNodeCollector submitNodeCollector, int i, CallbackInfo ci) {
        BoatRenderStateAccess access = (BoatRenderStateAccess)(Object) state;
        if (access.soulscorch$isNetherBoat() && access.soulscorch$isInLava()) {
            submitNodeCollector.submitModel(
                    this.soulscorch$lavaPatchModel,
                    Unit.INSTANCE,
                    poseStack,
                    RenderTypes.waterMask(),
                    i,
                    OverlayTexture.NO_OVERLAY,
                    state.outlineColor,
                    (ModelFeatureRenderer.CrumblingOverlay) null
            );
        }
    }
}