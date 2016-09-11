package com.creepgaming.usefulcompass.server;

import java.io.File;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class ServerEventHandler {

	@SubscribeEvent
	public void onJoin(PlayerLoggedInEvent event){
	EntityPlayer player = event.player;		
	
	if (isPlayerNew(player)){
		/* Since my old method of sending a server wide message doesn't seem to work anymore, here is the hacky thing: */
		
	List<EntityPlayerMP> list = FMLCommonHandler.instance().getMinecraftServerInstance().getServer().getPlayerList().getPlayerList();
	for(int i = 0; i <= list.size()-1; i++){
		
	list.get(i).addChatComponentMessage(new TextComponentString(TextFormatting.GOLD + "Welcome " + player.getDisplayNameString() + " to the server!"));
	}
	
	}
	}
	
	public static boolean isPlayerNew(EntityPlayer player){
		
		File dir = new File(player.getEntityWorld().getWorldInfo().getWorldName() + "/playerdata/" + player.getUniqueID() + ".dat");
		if (!dir.exists()){
			return true;
		}
		return false;
		}
	}

