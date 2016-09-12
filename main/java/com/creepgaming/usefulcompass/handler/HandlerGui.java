package com.creepgaming.usefulcompass.handler;

import com.creepgaming.usefulcompass.client.gui.GuiCompassBasic;
import com.creepgaming.usefulcompass.client.gui.GuiCompassBasicDelete;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class HandlerGui implements IGuiHandler {

	public static final int GUI_COMPASS_BASIC = 0;
	public static final int GUI_COMPASS_BASIC_DELETE = 1;
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUI_COMPASS_BASIC){
			return new GuiCompassBasic(player);
		}
		if (ID == GUI_COMPASS_BASIC_DELETE){
			return new GuiCompassBasicDelete(player);
		}
		return null;
	}

}
