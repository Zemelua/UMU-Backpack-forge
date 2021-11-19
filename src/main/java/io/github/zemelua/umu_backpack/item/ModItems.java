package io.github.zemelua.umu_backpack.item;

import io.github.zemelua.umu_backpack.UMUBackpack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModItems {
	private static final DeferredRegister<Item> REGISTRY = UMUBackpack.registry(ForgeRegistries.ITEMS);

	public static final RegistryObject<Item> BACKPACK = REGISTRY.register("backpack", ()
			-> new BackpackItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS))
	);

	private ModItems() {}

	private static boolean initialized = false;

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		if (initialized) throw new IllegalStateException("Items is already initialized!");

		REGISTRY.register(modBus);

		initialized = true;
	}
}
