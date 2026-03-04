package net.nullcoil.soulscorch.entity.client.soulless;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.piglin.AbstractPiglinModel;
import net.minecraft.client.model.monster.piglin.PiglinModel;
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

        // Welcome to the 1.21.4 Armor System!
        // ArmorModelSet.bake automatically wires up the Helmet, Chest, Legs, and Boots.
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                ArmorModelSet.bake(ModelLayers.ZOMBIFIED_PIGLIN_ARMOR, context.getModelSet(), AbstractPiglinModel::new),
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

        // Pass the twitch tracker integer so the model knows what to do!
        state.neutralTwitchState = entity.neutralTwitchState;

        // Copy animation states
        state.passiveAnimationState.copyFrom(entity.passiveAnimationState);
        state.neutralAnimationState.copyFrom(entity.neutralAnimationState);
        state.hostileAnimationState.copyFrom(entity.hostileAnimationState);
    }

    @Override
    public Identifier getTextureLocation(SoullessRenderState state) {
        return state.active ? AWAKENED : TEXTURE;
    }
}