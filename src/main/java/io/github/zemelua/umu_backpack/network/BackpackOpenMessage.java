package io.github.zemelua.umu_backpack.network;

import io.github.zemelua.umu_backpack.inventory.BackpackMenu;
import io.github.zemelua.umu_backpack.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class BackpackOpenMessage {
	public static void encode(BackpackOpenMessage message, FriendlyByteBuf buffer) {
	}

	@SuppressWarnings("InstantiationOfUtilityClass")
	public static BackpackOpenMessage decode(FriendlyByteBuf buffer) {
		return new BackpackOpenMessage();
	}

	public static void handle(BackpackOpenMessage message, Supplier<NetworkEvent.Context> networkContextSupplier) {
		NetworkEvent.Context networkContext = networkContextSupplier.get();
		ServerPlayer player = networkContext.getSender();
		if (player == null) return;
		ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
		if (!itemStack.is(ModItems.BACKPACK.get())) return;

		networkContext.enqueueWork(
				() -> NetworkHooks.openGui(player, new SimpleMenuProvider(
						(id, playerInventory, unused) -> new BackpackMenu(
								id, playerInventory, itemStack
						),
						new TranslatableComponent("container.umu_backpack.backpack")
				), buffer -> buffer.writeItem(itemStack))
		);
		networkContext.setPacketHandled(true);
	}
}
