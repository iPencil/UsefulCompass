package com.creepgaming.usefulcompass.proxy;

import com.creepgaming.usefulcompass.items.ItemRegister;
// import com.creepgaming.usefulcompass.server.ServerEventHandler;

// import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		ItemRegister.registerItems();
	}

	public void init(FMLInitializationEvent e) {
		// MinecraftForge.EVENT_BUS.register(new ServerEventHandler());

	}

	public void postInit(FMLPostInitializationEvent e) {

	}

}
