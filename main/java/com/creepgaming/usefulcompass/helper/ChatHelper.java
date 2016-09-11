package com.creepgaming.usefulcompass.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ChatHelper {

	/*
	 * Creates a chat message whenever a new location has been created
	 */
	
	public static void notifyLocationCreate(EntityPlayer p, String loc){
		
		p.addChatComponentMessage(new TextComponentString(TextFormatting.DARK_AQUA + "Location " + TextFormatting.GOLD + loc + TextFormatting.DARK_AQUA + " created and selected."));
		
		
	}
	
	public static void notifyLocationSelect(EntityPlayer p, String loc){
		
		p.addChatComponentMessage(new TextComponentString(TextFormatting.DARK_GREEN + "Location " + TextFormatting.GOLD + loc + TextFormatting.DARK_GREEN + " selected."));
	}
	
	
}
