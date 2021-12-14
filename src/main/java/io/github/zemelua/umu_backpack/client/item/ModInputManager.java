package io.github.zemelua.umu_backpack.client.item;

import io.github.zemelua.umu_backpack.network.BackpackOpenMessage;
import io.github.zemelua.umu_backpack.network.ModNetworkHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public final class ModInputManager {
	public static final KeyMapping KEY_BIND_BACKPACK = new KeyMapping("key.backpack", GLFW.GLFW_KEY_B, "key.categories.inventory");

	public static void onFMLClientSetup(final FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(KEY_BIND_BACKPACK);
	}

	@SuppressWarnings("InstantiationOfUtilityClass")
	public static void onClientTick(final TickEvent.ClientTickEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer player = minecraft.player;

		if (event.phase == TickEvent.Phase.START) {
			while (KEY_BIND_BACKPACK.consumeClick()) {
				if (player == null) break;

				ModNetworkHandler.CHANNEL.sendToServer(new BackpackOpenMessage());
			}
		}
	}
}
