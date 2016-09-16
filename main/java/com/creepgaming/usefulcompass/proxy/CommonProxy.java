package com.creepgaming.usefulcompass.proxy;

import com.creepgaming.usefulcompass.UsefulCompass;
import com.creepgaming.usefulcompass.handler.HandlerGui;
import com.creepgaming.usefulcompass.handler.HandlerPackets;
import com.creepgaming.usefulcompass.items.ItemRegister;
import com.creepgaming.usefulcompass.recipes.CompassRecipeHandler;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		ItemRegister.registerItems();
		HandlerPackets.registerMessages(UsefulCompass.MODID);
	}

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(UsefulCompass.instance, new HandlerGui());
		GameRegistry.addRecipe(new CompassRecipeHandler());
		
	}

	public void postInit(FMLPostInitializationEvent e) {

	}

}
