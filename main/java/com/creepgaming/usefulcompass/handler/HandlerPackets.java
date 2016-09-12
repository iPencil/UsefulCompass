package com.creepgaming.usefulcompass.handler;

import com.creepgaming.usefulcompass.network.packets.PacketInt;
import com.creepgaming.usefulcompass.network.packets.PacketInt.PacketIntHandler;
import com.creepgaming.usefulcompass.network.packets.PacketString;
import com.creepgaming.usefulcompass.network.packets.PacketString.PacketStringHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerPackets {

	public static SimpleNetworkWrapper INSTANCE = null;

	// public static final SimpleNetworkWrapper INSTANCE =
	// NetworkRegistry.INSTANCE.newSimpleChannel(UsefulCompass.MODID);

	private static int packetId = 0;

	public static int nextID() {
		return packetId++;
	}

	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}

	public static void registerMessages() {

		INSTANCE.registerMessage(PacketStringHandler.class, PacketString.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketIntHandler.class, PacketInt.class, nextID(), Side.SERVER);
	}

}
