package com.creepgaming.usefulcompass.client.gui;

import java.io.IOException;
import org.lwjgl.input.Keyboard;

import com.creepgaming.usefulcompass.handler.HandlerPackets;
import com.creepgaming.usefulcompass.network.packets.PacketString;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

public class GuiCompassBasic extends GuiScreen {

	public EntityPlayer player;
	private GuiTextField nameField;

	public GuiCompassBasic(EntityPlayer player) {
		this.player = player;
	}

	public GuiButton create;

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
		this.buttonList.add(this.create = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "Create"));
		Keyboard.enableRepeatEvents(true);
		this.nameField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
		this.nameField.setTextColor(-1);
		this.nameField.setDisabledTextColour(-1);
		this.nameField.setMaxStringLength(12);

	}

	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {

		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == this.create) {

			if (!this.nameField.getText().isEmpty()) {
				HandlerPackets.INSTANCE.sendToServer(new PacketString(this.nameField.getText()));
			} 

			this.mc.displayGuiScreen(null);
			if (this.mc.currentScreen == null)
				this.mc.setIngameFocus();
		}
	}

}
