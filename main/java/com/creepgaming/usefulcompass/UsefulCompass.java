package com.creepgaming.usefulcompass;

import org.apache.logging.log4j.Logger;
import com.creepgaming.usefulcompass.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = UsefulCompass.MODID, name = UsefulCompass.MODNAME, version = UsefulCompass.VERSION, useMetadata = true)
public class UsefulCompass {

	public static final String MODID = "usefulcompass";
	public static final String MODNAME = "UsefulCompass";
	public static final String VERSION = "1.0.0";

	@SidedProxy(clientSide = "com.creepgaming.usefulcompass.proxy.ClientProxy", serverSide = "com.creepgaming.usefulcompass.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static UsefulCompass instance;

	public static Logger logger;

	public static Boolean isDebug = true;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
		
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}
