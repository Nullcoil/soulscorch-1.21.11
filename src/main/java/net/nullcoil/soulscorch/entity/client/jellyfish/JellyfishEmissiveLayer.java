package net.nullcoil.soulscorch.entity.client.jellyfish;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.nullcoil.soulscorch.Soulscorch;

@Environment(EnvType.CLIENT)
public class JellyfishEmissiveLayer extends RenderLayer<JellyfishRenderState, JellyfishModel> {
    private static final Identifier GLOWEY_BITS =
            Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "textures/entity/hytodom_emissive.png");

    public JellyfishEmissiveLayer(RenderLayerParent<JellyfishRenderState, JellyfishModel> context) {
        super(context);
    }

    @Override
    public void submit(PoseStack matrices, SubmitNodeCollector buffer, int light, JellyfishRenderState state, float limbAngle, float limbDistance) {

        // 15728880 forces the translucent pass to render at max brightness (emissive)
        buffer.order(0).submitModel(
                this.getParentModel(),
                state,
                matrices,
                RenderTypes.entityTranslucent(GLOWEY_BITS), // Make sure it's RenderTypes with an 's'!
                15728880,
                LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                0xFFFFFFFF,
                (net.minecraft.client.renderer.texture.TextureAtlasSprite) null,
                state.outlineColor,
                (net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay) null
        );
    }
}