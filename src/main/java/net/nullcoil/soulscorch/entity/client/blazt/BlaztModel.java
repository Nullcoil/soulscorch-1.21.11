package net.nullcoil.soulscorch.entity.client.blazt;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import net.nullcoil.soulscorch.Soulscorch;

public class BlaztModel extends EntityModel<BlaztRenderState> {
    public static final ModelLayerLocation BLAZT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "blazt"), "main");

    private final ModelPart tentacles;
    private final ModelPart bb_main;
    private final ModelPart inner;
    private final ModelPart outer;

    // We store the BAKED animations here now
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation aggroAnimation;

    public BlaztModel(ModelPart root) {
        super(root);
        this.tentacles = root.getChild("tentacles");
        this.bb_main = root.getChild("bb_main");
        this.inner = this.tentacles.getChild("inner");
        this.outer = this.tentacles.getChild("outer");

        // Bake the animations right when the model is instantiated!
        // This is the new 1.21.2+ way of handling entity animations.
        this.idleAnimation = BlaztAnimations.IDLE.bake(root);
        this.aggroAnimation = BlaztAnimations.AGGRO.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition tentacles = partdefinition.addOrReplaceChild("tentacles", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition inner = tentacles.addOrReplaceChild("inner", CubeListBuilder.create().texOffs(0, 0).addBox(3.0F, -42.0F, -9.0F, 6.0F, 42.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -42.0F, -9.0F, 6.0F, 42.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(3.0F, -42.0F, 3.0F, 6.0F, 42.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -42.0F, 3.0F, 6.0F, 42.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition outer = tentacles.addOrReplaceChild("outer", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = outer.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -20.5F, -3.0F, 6.0F, 41.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -4.5F, 11.0F, -0.2618F, 2.3562F, 0.0F));
        PartDefinition cube_r2 = outer.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -20.5F, -3.0F, 6.0F, 41.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -4.5F, 11.0F, -0.2618F, -2.3562F, 0.0F));
        PartDefinition cube_r3 = outer.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -20.5F, -3.0F, 6.0F, 41.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -4.5F, -11.0F, -0.2618F, 0.7854F, 0.0F));
        PartDefinition cube_r4 = outer.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -20.5F, -3.0F, 6.0F, 41.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -4.5F, -11.0F, -0.2618F, -0.7854F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -48.0F, -24.0F, 48.0F, 48.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 192, 96);
    }

    @Override
    public void setupAnim(BlaztRenderState state) {
        super.setupAnim(state);
        // The reset pose call is now handled internally by super.setupAnim(state) for standard models!

        // Apply the baked animations
        if (state.shooting) {
            this.aggroAnimation.apply(state.shootAnimationState, state.ageInTicks);
        } else {
            this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        }
    }
}