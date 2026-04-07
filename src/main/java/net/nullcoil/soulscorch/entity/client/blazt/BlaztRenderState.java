package net.nullcoil.soulscorch.entity.client.blazt;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class BlaztRenderState extends LivingEntityRenderState {
    public boolean shooting;
    public boolean bullrushing;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
}