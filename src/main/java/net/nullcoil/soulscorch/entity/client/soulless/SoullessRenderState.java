package net.nullcoil.soulscorch.entity.client.soulless;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class SoullessRenderState extends PiglinRenderState {
    public SoullessActivity currentActivity = SoullessActivity.PASSIVE;
    public boolean active = false;

    public int neutralTwitchState = -1;
    public final AnimationState passiveAnimationState = new AnimationState();
    public final AnimationState neutralAnimationState = new AnimationState();
    public final AnimationState hostileAnimationState = new AnimationState();
}