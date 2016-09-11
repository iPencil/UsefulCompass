package com.creepgaming.usefulcompass.proxy;

import com.creepgaming.usefulcompass.UsefulCompass;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		ModelLoader.setCustomModelResourceLocation(UsefulCompass.itemCompass, 0, new ModelResourceLocation("usefulcompass:item_compass", "inventory"));
		super.preInit(e);
	}
	
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		
	}


}
