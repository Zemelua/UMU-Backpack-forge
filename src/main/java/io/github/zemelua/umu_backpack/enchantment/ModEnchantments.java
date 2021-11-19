package io.github.zemelua.umu_backpack.enchantment;

import io.github.zemelua.umu_backpack.UMUBackpack;
import io.github.zemelua.umu_backpack.item.BackpackItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModEnchantments {
	private static final DeferredRegister<Enchantment> REGISTRY = UMUBackpack.registry(ForgeRegistries.ENCHANTMENTS);

	public static final RegistryObject<Enchantment> CRAM = REGISTRY.register("cram", ()
			-> new CramEnchantment(Enchantment.Rarity.RARE, Categories.BACKPACK, EquipmentSlot.CHEST)
	);
	public static final RegistryObject<Enchantment> BACK_PROTECTION = REGISTRY.register("back_protection", ()
			-> new BackProtectionEnchantment(Enchantment.Rarity.UNCOMMON, Categories.BACKPACK, EquipmentSlot.CHEST)
	);

	private ModEnchantments() {}

	private static boolean initialized = false;

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		if (initialized) throw new IllegalStateException("Enchantments is already initialized!");

		REGISTRY.register(modBus);
		forgeBus.addListener(BackProtectionEnchantment::onLivingHurt);

		initialized = true;
	}

	public final static class Categories {
		public static final EnchantmentCategory BACKPACK = EnchantmentCategory.create("backpack", (item -> item instanceof BackpackItem));
	}
}
