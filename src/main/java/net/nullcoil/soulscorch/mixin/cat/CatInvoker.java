package net.nullcoil.soulscorch.mixin.cat;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Cat.class)
public interface CatInvoker {
    @Invoker("setVariant")
    void invokeSetVariant(Holder<CatVariant> variant);

    @Invoker("setCollarColor")
    void invokeSetCollarColor(DyeColor dyeColor);
}
