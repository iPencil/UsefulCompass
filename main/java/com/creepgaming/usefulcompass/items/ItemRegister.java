package com.creepgaming.usefulcompass.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegister {

	public static Item itemCompass, itemGPS;

	public static final void registerItems() {

		itemGPS = new ItemGPS().setUnlocalizedName("itemgps").setCreativeTab(CreativeTabs.TOOLS);
		GameRegistry.register(itemGPS.setRegistryName("item_gps"));
		
		itemCompass = new ItemCompass().setUnlocalizedName("itemcompass").setCreativeTab(CreativeTabs.TOOLS);
		GameRegistry.register(itemCompass.setRegistryName("item_compass"));
	}

}
