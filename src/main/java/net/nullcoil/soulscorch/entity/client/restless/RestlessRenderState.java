package net.nullcoil.soulscorch.entity.client.restless;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class RestlessRenderState extends LivingEntityRenderState {
    public boolean awakened;
    public int movementCooldownTicks;
    public float age;
}