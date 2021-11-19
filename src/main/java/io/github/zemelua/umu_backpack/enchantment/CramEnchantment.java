package io.github.zemelua.umu_backpack.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CramEnchantment extends Enchantment {
	protected CramEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}
}
