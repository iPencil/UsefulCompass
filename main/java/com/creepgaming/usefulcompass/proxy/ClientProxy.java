package com.creepgaming.usefulcompass.proxy;

import com.creepgaming.usefulcompass.items.ItemRegister;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		ModelLoader.setCustomModelResourceLocation(ItemRegister.itemCompass, 0,
				new ModelResourceLocation("usefulcompass:item_compass", "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemRegister.itemGPS, 0,
				new ModelResourceLocation("usefulcompass:item_gps", "inventory"));
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);

	}

}
