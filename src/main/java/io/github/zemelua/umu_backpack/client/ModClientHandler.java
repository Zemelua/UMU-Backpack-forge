package io.github.zemelua.umu_backpack.client;

import io.github.zemelua.umu_backpack.client.gui.BackpackScreen;
import io.github.zemelua.umu_backpack.client.input.ModInputHandler;
import io.github.zemelua.umu_backpack.inventory.ModContainers;
import net.minecraft.client.gui.screens.MenuScreens;
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
		this.modBus.addListener(ModInputHandler::onFMLClientSetup);
		this.forgeBus.addListener(ModInputHandler::onClientTick);

		this.initialized = true;
	}

	private static void onFMLClientSetup(final FMLClientSetupEvent event) {
		MenuScreens.register(ModContainers.BACKPACK.get(), BackpackScreen::new);
	}
}
