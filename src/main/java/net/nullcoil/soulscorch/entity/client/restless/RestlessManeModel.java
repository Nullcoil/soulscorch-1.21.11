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
import net.nullcoil.soulscorch.Soulscorch;

@Environment(EnvType.CLIENT)
public class RestlessManeModel extends EntityModel<RestlessRenderState> {
    public static final ModelLayerLocation MANE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "restless"), "mane");

    private final ModelPart body;
    private final ModelPart mane;

    public RestlessManeModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.mane = this.body.getChild("mane");
    }

    public static LayerDefinition createManeLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        // Empty body, just acts as a pivot point for the mane
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        body.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(90, 33).addBox(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -14.0F, -7.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(RestlessRenderState state) {
        // Handled dynamically by the Layer!
    }

    public ModelPart getBody() { return body; }
}