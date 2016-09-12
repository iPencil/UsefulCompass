package com.creepgaming.usefulcompass.client.gui;

import java.io.IOException;
import org.lwjgl.input.Keyboard;

import com.creepgaming.usefulcompass.handler.HandlerPackets;
import com.creepgaming.usefulcompass.items.ItemRegister;
import com.creepgaming.usefulcompass.network.packets.PacketInt;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GuiCompassBasicDelete extends GuiScreen {

	public EntityPlayer player;
	private GuiTextField nameField;

	public GuiCompassBasicDelete(EntityPlayer player) {
		this.player = player;
	}

	public GuiButton delete;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.nameField.drawTextBox();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {

		this.buttonList.clear();
		this.buttonList.add(this.delete = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "Delete"));
		this.nameField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
		this.nameField.setTextColor(-1);
		this.nameField.setDisabledTextColour(-1);
		this.nameField.setMaxStringLength(12);

		if (player.getHeldItemMainhand() != null
				&& player.getHeldItemMainhand().getItem() == ItemRegister.itemCompass) {

			ItemStack stack = player.getHeldItemMainhand();
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Selector")) {

				String waypoint = stack.getTagCompound()
						.getString("name" + stack.getTagCompound().getInteger("Selector"));

				this.nameField.setText(waypoint);

			}
		}

	}

	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == this.delete) {

			HandlerPackets.INSTANCE.sendToServer(new PacketInt(1));

			this.mc.displayGuiScreen(null);
			if (this.mc.currentScreen == null)
				this.mc.setIngameFocus();
		}
	}

}
