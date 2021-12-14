package io.github.zemelua.umu_backpack.client;

import io.github.zemelua.umu_backpack.client.gui.BackpackScreen;
import io.github.zemelua.umu_backpack.client.item.ModInputManager;
import io.github.zemelua.umu_backpack.client.model.ModModelLayers;
import io.github.zemelua.umu_backpack.inventory.ModMenus;
import io.github.zemelua.umu_backpack.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("FieldCanBeLocal")
public class ModClientHandler {
	private final IEventBus forgeBus;
	private final IEventBus modBus;

	private boolean initialized;

	public ModClientHandler(IEventBus forgeBus, IEventBus modBus) {
		this.forgeBus = forgeBus;
		this.modBus = modBus;

		this.initialized = false;
	}

	public void initialize() {
		if (initialized) throw new IllegalStateException("Client is already initialized!");

		this.modBus.addListener(ModClientHandler::onFMLClientSetup);
		this.modBus.addListener(ModClientHandler::onColorHandlerItem);
		this.modBus.addListener(ModModelLayers::onRegisterLayerDefinitions);
		this.modBus.addListener(ModInputManager::onFMLClientSetup);
		this.forgeBus.addListener(ModInputManager::onClientTick);

		this.initialized = true;
	}

	private static void onFMLClientSetup(final FMLClientSetupEvent event) {
		MenuScreens.register(ModMenus.BACKPACK.get(), BackpackScreen::new);
	}

	private static void onColorHandlerItem(final ColorHandlerEvent.Item event) {
		event.getItemColors().register(
				(itemStack, color) -> color > 0 ? -1 : ((DyeableArmorItem) itemStack.getItem()).getColor(itemStack),
				ModItems.BACKPACK.get()
		);
	}
}
