package io.github.zemelua.umu_backpack.network;

import io.github.zemelua.umu_backpack.inventory.BackpackContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

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

		networkContext.enqueueWork(
				() -> NetworkHooks.openGui(player, new SimpleMenuProvider(
						(id, playerInventory, unused) -> new BackpackContainer(
								id, playerInventory, player.getItemBySlot(EquipmentSlot.CHEST)
						),
						new TranslatableComponent("container.umu_backpack.backpack")
				))
		);
		networkContext.setPacketHandled(true);
	}
}
