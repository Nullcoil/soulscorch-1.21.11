package net.nullcoil.soulscorch.entity.client.jellyfish;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class JellyfishModel extends EntityModel<JellyfishRenderState> {
    public static final ModelLayerLocation HYTODOM = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "hytodom"), "main");

    private final ModelPart root;
    private final ModelPart bell;
    private final ModelPart fringe;

    // The new 1.21.4 cached animation object
    private final KeyframeAnimation idleAnimation;

    public JellyfishModel(ModelPart root) {
        super(root);
        this.root = root;
        this.bell = root.getChild("bell");
        this.fringe = this.bell.getChild("fringe");

        // Bake the animation directly to the root part on startup!
        this.idleAnimation = JellyfishAnimations.IDLE.bake(this.root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        PartDefinition bell = root.addOrReplaceChild("bell", CubeListBuilder.create().texOffs(40, 20)
                .addBox(-5.0F, -36.0F, -5.0F, 10.0F, 4.0F, 10.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        bell.addOrReplaceChild("tentacle_1", CubeListBuilder.create().texOffs(20, 20)
                .addBox(0.0F, -1.0F, -5.0F, 0.0F, 32.0F, 10.0F,
                        new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -32.0F, 0.0F,
                0.0F, 0.7854F, 0.0F));

        bell.addOrReplaceChild("tentacle_2", CubeListBuilder.create().texOffs(0, 20)
                .addBox(0.0F, -1.0F, -5.0F, 0.0F, 32.0F, 10.0F,
                        new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -32.0F, 0.0F,
                0.0F, -0.7854F, 0.0F));

        bell.addOrReplaceChild("fringe", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-8.0F, 0.0F, -8.0F, 16.0F, 4.0F, 16.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, -34.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 80, 62);
    }

    @Override
    public void setupAnim(JellyfishRenderState state) {
        super.setupAnim(state);
        this.root.getAllParts().forEach(ModelPart::resetPose);

        float t = (state.ageInTicks % (3.5f * 20f)) / 20f;
        float reverseT = 3.5f - t;
        float theta = (float)(2.0 * Math.PI * reverseT / 3.5f);

        float bellY = -0.5f * ((float)Math.cos(theta) + 1.0f);
        bell.y += bellY;

        float scaleY = 2.0f + (float)Math.cos(theta);
        fringe.yScale *= scaleY;

        float xzTheta = theta - (float)(Math.PI * 0.6f);
        float scaleXZ = 0.85f + 0.15f * (float)Math.cos(xzTheta);
        fringe.xScale *= scaleXZ;
        fringe.zScale *= scaleXZ;

        float bobOffset = (float)Math.cos(xzTheta) * (2.0f / 16.0f);
        fringe.y += bobOffset;
    }
}