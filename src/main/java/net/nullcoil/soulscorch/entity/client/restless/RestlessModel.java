package net.nullcoil.soulscorch.entity.client.restless;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.util.Mth;
import net.nullcoil.soulscorch.Soulscorch;

import java.util.List;

@Environment(EnvType.CLIENT)
public class RestlessModel extends EntityModel<RestlessRenderState> {
    public static final ModelLayerLocation RESTLESS = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "restless"), "main"
    );

    private static final float HEAD_PITCH_START = 0.87266463F;
    private static final float HEAD_PITCH_END = -0.34906584F;

    private final ModelPart head;
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private final ModelPart body;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart mane;

    public RestlessModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.mane = this.body.getChild("mane");
        this.head = root.getChild("head");
        this.rightEar = this.head.getChild("right_ear");
        this.leftEar = this.head.getChild("left_ear");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(1, 1).addBox(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F),
                PartPose.offset(0.0F, 7.0F, 0.0F)
        );

        body.addOrReplaceChild("mane",
                CubeListBuilder.create().texOffs(90, 33).addBox(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, -14.0F, -7.0F)
        );

        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(61, 1).addBox(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F),
                PartPose.offsetAndRotation(0.0F, 2.0F, -12.0F, 0.87266463F, 0.0F, 0.0F)
        );

        head.addOrReplaceChild("right_ear",
                CubeListBuilder.create().texOffs(1, 1).addBox(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(-6.0F, -2.0F, -3.0F, 0.0F, 0.0F, -0.6981317F)
        );

        head.addOrReplaceChild("left_ear",
                CubeListBuilder.create().texOffs(1, 6).addBox(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(6.0F, -2.0F, -3.0F, 0.0F, 0.0F, 0.6981317F)
        );

        head.addOrReplaceChild("right_horn",
                CubeListBuilder.create().texOffs(10, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F),
                PartPose.offset(-7.0F, 2.0F, -12.0F)
        );

        head.addOrReplaceChild("left_horn",
                CubeListBuilder.create().texOffs(1, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F),
                PartPose.offset(7.0F, 2.0F, -12.0F)
        );

        root.addOrReplaceChild("right_front_leg",
                CubeListBuilder.create().texOffs(66, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F),
                PartPose.offset(-4.0F, 10.0F, -8.5F)
        );
        root.addOrReplaceChild("left_front_leg",
                CubeListBuilder.create().texOffs(41, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F),
                PartPose.offset(4.0F, 10.0F, -8.5F)
        );
        root.addOrReplaceChild("right_hind_leg",
                CubeListBuilder.create().texOffs(21, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F),
                PartPose.offset(-5.0F, 13.0F, 10.0F)
        );
        root.addOrReplaceChild("left_hind_leg",
                CubeListBuilder.create().texOffs(0, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F),
                PartPose.offset(5.0F, 13.0F, 10.0F)
        );

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(RestlessRenderState state) {
        super.setupAnim(state);

        float walkAnim = state.walkAnimationSpeed;
        float walkPos = state.walkAnimationPos;

        this.rightEar.zRot = -0.6981317F - walkAnim * Mth.sin(walkPos);
        this.leftEar.zRot = 0.6981317F + walkAnim * Mth.sin(walkPos);
        this.head.yRot = state.yRot * ((float)Math.PI / 180F);

        float cooldownLerp = 1.0F - (float)Math.abs(10 - 2 * state.movementCooldownTicks) / 10.0F;
        this.head.xRot = Mth.lerp(cooldownLerp, HEAD_PITCH_START, HEAD_PITCH_END);

        if (state.isBaby) {
            this.head.y += cooldownLerp * 2.5F;
            this.mane.z += 4.0f; // Push mane back slightly for babies
        }

        this.rightFrontLeg.xRot = Mth.cos(walkPos) * 1.2F * walkAnim;
        this.leftFrontLeg.xRot = Mth.cos(walkPos + (float)Math.PI) * 1.2F * walkAnim;
        this.rightHindLeg.xRot = this.leftFrontLeg.xRot;
        this.leftHindLeg.xRot = this.rightFrontLeg.xRot;
    }

    public List<ModelPart> getMane() {
        return List.of(this.mane);
    }

    public ModelPart getBody() {
        return this.body;
    }
}