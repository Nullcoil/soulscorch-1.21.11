package net.nullcoil.soulscorch.entity.client.companion;

import net.minecraft.client.model.animal.feline.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.resources.Identifier;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.entity.ai.SoulborneCatEntity;

public class SoulCatRenderer extends AgeableMobRenderer<SoulborneCatEntity, CatRenderState, CatModel> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "textures/entity/midnight.png");

    public SoulCatRenderer(EntityRendererProvider.Context context) {
        super(context, new CatModel(context.bakeLayer(ModelLayers.CAT)), new CatModel(context.bakeLayer(ModelLayers.CAT_BABY)), .4f);

        this.addLayer(new CatCollarLayer(this, context.getModelSet()));
    }

    @Override
    public Identifier getTextureLocation(CatRenderState livingEntityRenderState) {
        return TEXTURE;
    }

    @Override
    public CatRenderState createRenderState() {
        return new CatRenderState();
    }

    @Override
    public void extractRenderState(SoulborneCatEntity entity, CatRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        // 1. Core poses
        state.isSitting = entity.isInSittingPose();
        state.isLyingOnTopOfSleepingPlayer = entity.isLying();
        state.collarColor = entity.getCollarColor();

        // 2. Smooth animation transitions for lying down/relaxing
        state.lieDownAmount = entity.getLieDownAmount(partialTick);
        state.lieDownAmountTail = entity.getLieDownAmountTail(partialTick);
        state.relaxStateOneAmount = entity.getRelaxStateOneAmount(partialTick);
    }
}
