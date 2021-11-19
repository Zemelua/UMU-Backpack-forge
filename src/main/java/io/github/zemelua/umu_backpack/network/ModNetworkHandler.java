package io.github.zemelua.umu_backpack.network;

import io.github.zemelua.umu_backpack.UMUBackpack;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public final class ModNetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			UMUBackpack.location("main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void initialize() {
		CHANNEL.registerMessage(0, BackpackOpenMessage.class, BackpackOpenMessage::encode, BackpackOpenMessage::decode, BackpackOpenMessage::handle);
	}
}
