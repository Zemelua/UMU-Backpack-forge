package io.github.zemelua.umu_backpack.inventory;

import io.github.zemelua.umu_backpack.UMUBackpack;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModContainers {
	private static final DeferredRegister<MenuType<?>> REGISTRY = UMUBackpack.registry(ForgeRegistries.CONTAINERS);

	public static final RegistryObject<MenuType<BackpackContainer>> BACKPACK = REGISTRY.register(UMUBackpack.MOD_ID, ()
			-> IForgeContainerType.create(BackpackContainer::new)
	);

	private ModContainers() {}

	private static boolean initialized = false;

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		if (initialized) throw new IllegalStateException("Containers is already initialized!");

		REGISTRY.register(modBus);

		initialized = true;
	}
}
