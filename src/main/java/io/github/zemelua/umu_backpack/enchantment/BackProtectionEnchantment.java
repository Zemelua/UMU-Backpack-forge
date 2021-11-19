package io.github.zemelua.umu_backpack.enchantment;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BackProtectionEnchantment extends Enchantment {
	protected BackProtectionEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	protected static void onLivingHurt(final LivingHurtEvent event) {
		DamageSource damageSource = event.getSource();
		LivingEntity living = event.getEntityLiving();
		int backProtectionLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BACK_PROTECTION.get(), living);

		if (backProtectionLevel > 0 && !damageSource.isBypassArmor()) {
			Vec3 damagePos = damageSource.getSourcePosition();
			if (damagePos == null) return;

			Vec3 livingPos = living.position();
			Vec3 lookVec = living.getLookAngle();
			Vec3 damageVec = damagePos.subtract(livingPos).reverse().normalize();

			if (damageVec.dot(lookVec) > 0.0D) {
				float amount = event.getAmount();
				event.setAmount(Math.min(amount - amount * backProtectionLevel * 8.0F / 100.0F, 80));
			}
		}
	}
}
