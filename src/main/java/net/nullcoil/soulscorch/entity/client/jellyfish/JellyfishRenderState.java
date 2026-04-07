package net.nullcoil.soulscorch.entity.client.jellyfish;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class JellyfishRenderState extends LivingEntityRenderState {
    public static final AnimationState IDLE = new AnimationState();
    public static float velocityX;
    public static float velocityY;
    public static float velocityZ;
    public float bodyYaw;
    public float spinSpeed;
    public static boolean isDying;
}