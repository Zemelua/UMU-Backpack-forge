package io.github.zemelua.umu_backpack;

import io.github.zemelua.umu_backpack.client.ModClientHandler;
import io.github.zemelua.umu_backpack.enchantment.ModEnchantments;
import io.github.zemelua.umu_backpack.inventory.ModMenus;
import io.github.zemelua.umu_backpack.item.ModItems;
import io.github.zemelua.umu_backpack.network.ModNetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod(UMUBackpack.MOD_ID)
public class UMUBackpack {
	public static final String MOD_ID = "umu_backpack";
	public static final Logger LOGGER = LogManager.getLogger();
	private static final Marker MARKER = MarkerManager.getMarker("UMU_BACKPACK");

	public UMUBackpack() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.initialize(forgeBus, modBus);
		ModEnchantments.initialize(forgeBus, modBus);
		ModMenus.initialize(forgeBus, modBus);
		ModNetworkHandler.initialize();

		ModClientHandler clientHandler = new ModClientHandler(forgeBus, modBus);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientHandler::initialize);
	}

	public static ResourceLocation location(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> registry(IForgeRegistry<T> type) {
		return DeferredRegister.create(type, MOD_ID);
	}
}
