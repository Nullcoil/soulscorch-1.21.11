package net.nullcoil.soulscorch.entity.client.soulless;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.resources.Identifier;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.entity.ai.SoullessEntity;

@Environment(EnvType.CLIENT)
public class SoullessRenderer extends HumanoidMobRenderer<SoullessEntity, SoullessRenderState, SoullessModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "textures/entity/soulless/soulless.png");
    private static final Identifier AWAKENED = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "textures/entity/soulless/awakened.png");

    public SoullessRenderer(EntityRendererProvider.Context context) {
        super(context, new SoullessModel(context.bakeLayer(SoullessModel.SOULLESS)), 0.5f);

        // THE FIX: Pass SoullessModel::new here so the armor inherits your twitches!
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                ArmorModelSet.bake(ModelLayers.ZOMBIFIED_PIGLIN_ARMOR, context.getModelSet(), SoullessModel::new),
                context.getEquipmentRenderer()
        ));
    }

    @Override
    public SoullessRenderState createRenderState() {
        return new SoullessRenderState();
    }

    @Override
    public void extractRenderState(SoullessEntity entity, SoullessRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.currentActivity = entity.getActivity();
        state.active = state.currentActivity != SoullessActivity.PASSIVE;

        state.neutralTwitchState = entity.neutralTwitchState;

        state.passiveAnimationState.copyFrom(entity.passiveAnimationState);
        state.neutralAnimationState.copyFrom(entity.neutralAnimationState);
        state.hostileAnimationState.copyFrom(entity.hostileAnimationState);
    }

    @Override
    public Identifier getTextureLocation(SoullessRenderState state) {
        return state.active ? AWAKENED : TEXTURE;
    }
}