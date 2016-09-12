package com.creepgaming.usefulcompass.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ChatHelper {

	/*
	 * Creates a chat message whenever a new location has been created
	 */
	
	public static void notifyLocationCreate(EntityPlayer p, String loc){
		
		p.addChatComponentMessage(new TextComponentString(TextFormatting.GOLD + loc + TextFormatting.DARK_AQUA + " created and selected."));
	}
	
	public static void notifyLocationDelete(EntityPlayer p, String loc){
		
		p.addChatComponentMessage(new TextComponentString(TextFormatting.GOLD + loc + TextFormatting.DARK_AQUA + " was deleted."));
	}
	
	public static void notifyLocationSelect(EntityPlayer p, String loc){
		
		p.addChatComponentMessage(new TextComponentString(TextFormatting.GOLD + loc + TextFormatting.DARK_GREEN + " selected."));
	}
	
	public static void notifyPlayerLocationFull(EntityPlayer p){
		p.addChatComponentMessage(new TextComponentString(TextFormatting.DARK_AQUA + "Cannot store more than 10 locations!"));
		
	}
	
	
}
