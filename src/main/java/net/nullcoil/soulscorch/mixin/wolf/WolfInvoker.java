package net.nullcoil.soulscorch.mixin.wolf;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Wolf.class)
public interface WolfInvoker {
    @Invoker("setVariant")
    void invokeSetVariant(Holder<WolfVariant> variant);

    @Invoker("getVariant")
    Holder<WolfVariant> invokeGetVariant();

    @Invoker("setCollarColor")
    void invokeSetCollarColor(DyeColor dyeColor);
}
