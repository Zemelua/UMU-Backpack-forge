package io.github.zemelua.umu_backpack.inventory;

import io.github.zemelua.umu_backpack.UMUBackpack;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModMenus {
	private static final DeferredRegister<MenuType<?>> REGISTRY = UMUBackpack.registry(ForgeRegistries.CONTAINERS);

	public static final RegistryObject<MenuType<BackpackMenu>> BACKPACK = REGISTRY.register("backpack", ()
			-> IForgeMenuType.create(BackpackMenu::new)
	);

	private ModMenus() {}

	private static boolean initialized = false;

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		if (initialized) throw new IllegalStateException("Menus is already initialized!");

		REGISTRY.register(modBus);

		initialized = true;
	}
}
