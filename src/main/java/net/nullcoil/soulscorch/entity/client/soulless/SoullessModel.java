package net.nullcoil.soulscorch.entity.client.soulless;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.monster.piglin.AbstractPiglinModel;
import net.minecraft.client.model.monster.piglin.PiglinModel;
import net.minecraft.resources.Identifier;
import net.nullcoil.soulscorch.Soulscorch;

@Environment(EnvType.CLIENT)
public class SoullessModel extends AbstractPiglinModel<SoullessRenderState> {
    public static final ModelLayerLocation SOULLESS = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "soulless"), "main");

    private final KeyframeAnimation passiveAnimation;
    private final KeyframeAnimation neutralHeadTwitch0;
    private final KeyframeAnimation neutralHeadTwitch1;
    private final KeyframeAnimation neutralArmTwitch;

    public SoullessModel(ModelPart root) {
        super(root);
        this.passiveAnimation = SoullessAnimations.PASSIVE.bake(root);
        this.neutralHeadTwitch0 = SoullessAnimations.NEUTRAL_HEAD_TWITCH0.bake(root);
        this.neutralHeadTwitch1 = SoullessAnimations.NEUTRAL_HEAD_TWITCH1.bake(root);
        this.neutralArmTwitch = SoullessAnimations.NEUTRAL_ARM_TWITCH.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = PiglinModel.createMesh(CubeDeformation.NONE);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(SoullessRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        switch (state.currentActivity) {
            case PASSIVE -> {
                this.body.xRot = (float) Math.toRadians(20);
                this.head.xRot = (float) Math.toRadians(40);
                this.head.yRot = (float) Math.toRadians(-12);
                this.head.zRot = (float) Math.toRadians(-4);
                this.leftLeg.z += 4;
                this.rightLeg.z += 4;
                this.leftLeg.xRot = (float) Math.toRadians(10);
                this.leftLeg.zRot = (float) Math.toRadians(-5);
                this.rightLeg.xRot = (float) Math.toRadians(-10);
                this.rightLeg.yRot = (float) Math.toRadians(-7);
                this.rightLeg.zRot = (float) Math.toRadians(5);

                this.passiveAnimation.apply(state.passiveAnimationState, state.ageInTicks);
            }
            case NEUTRAL -> {
                super.setupAnim(state);
                this.leftLeg.z += 3;
                this.rightLeg.z += 3;
                this.leftLeg.xRot = (float) Math.toRadians(10);
                this.leftLeg.zRot = (float) Math.toRadians(-5);
                this.rightLeg.xRot = (float) Math.toRadians(-10);
                this.rightLeg.yRot = (float) Math.toRadians(-7);
                this.rightLeg.zRot = (float) Math.toRadians(5);
                this.head.xRot += (float) Math.toRadians(14.6599);
                this.head.yRot += (float) Math.toRadians(3.2113);
                this.head.zRot += (float) Math.toRadians(-12.0868);
                this.body.xRot = (float) Math.toRadians(17.5);

                if (state.neutralTwitchState == 0) this.neutralHeadTwitch0.apply(state.neutralAnimationState, state.ageInTicks);
                else if (state.neutralTwitchState == 1) this.neutralHeadTwitch1.apply(state.neutralAnimationState, state.ageInTicks);
                else if (state.neutralTwitchState == 2) this.neutralArmTwitch.apply(state.neutralAnimationState, state.ageInTicks);
            }
            case HOSTILE -> {
                super.setupAnim(state);

                // Directly applying the Zombie arm math to bypass the UndeadRenderState bounds!
                float attackProgress = state.attackTime;
                float defaultPitch = -(float)Math.PI / 1.5F; // 1.5F is the aggressive pose

                float swingSin1 = net.minecraft.util.Mth.sin(attackProgress * (float)Math.PI);
                float swingSin2 = net.minecraft.util.Mth.sin((1.0F - (1.0F - attackProgress) * (1.0F - attackProgress)) * (float)Math.PI);

                this.rightArm.zRot = 0.0F;
                this.rightArm.yRot = -(0.1F - swingSin1 * 0.6F);
                this.rightArm.xRot = defaultPitch + (swingSin1 * 1.2F - swingSin2 * 0.4F);

                this.leftArm.zRot = 0.0F;
                this.leftArm.yRot = 0.1F - swingSin1 * 0.6F;
                this.leftArm.xRot = defaultPitch + (swingSin1 * 1.2F - swingSin2 * 0.4F);

                // Apply the standard breathing bobbing to the arms
                net.minecraft.client.model.AnimationUtils.bobArms(this.rightArm, this.leftArm, state.ageInTicks);
            }
        }
    }
}