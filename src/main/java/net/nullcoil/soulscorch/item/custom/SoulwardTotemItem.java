package net.nullcoil.soulscorch.item.custom;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.nullcoil.soulscorch.item.ModItems;

public class SoulwardTotemItem extends Item {
    public SoulwardTotemItem(Properties properties) {
        super(properties.durability(100).repairable(ModItems.SOUL_SHARD));
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, Holder<Enchantment> enchantment, EnchantingContext context) {
        return enchantment.is(Enchantments.UNBREAKING) ||
                enchantment.is(Enchantments.MENDING);
    }
}
