package com.creepgaming.usefulcompass.items;

import java.util.List;

import com.creepgaming.usefulcompass.UsefulCompass;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGPS extends Item implements IEnergyContainerItem{

	protected int capacity = 1000;
	protected int maxRecieve = 1000;
	protected int maxExtract = 1000;
	
	public ItemGPS(){
			
	}
	

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

		tooltip.add("Energy: " + this.getEnergyStored(stack) + "RF");
		
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	public ItemGPS(int capacity) {

		this(capacity, capacity, capacity);
	}

	public ItemGPS(int capacity, int maxTransfer) {

		this(capacity, maxTransfer, maxTransfer);
	}

	public ItemGPS(int capacity, int maxReceive, int maxExtract) {

		this.capacity = capacity;
		this.maxRecieve = maxReceive;
		this.maxExtract = maxExtract;
	}

	public ItemGPS setCapacity(int capacity) {

		this.capacity = capacity;
		return this;
	}

	public ItemGPS  setMaxTransfer(int maxTransfer) {

		setMaxReceive(maxTransfer);
		setMaxExtract(maxTransfer);
		return this;
	}

	public ItemGPS  setMaxReceive(int maxReceive) {

		this.maxRecieve = maxReceive;
		return this;
	}

	public ItemGPS  setMaxExtract(int maxExtract) {

		this.maxExtract = maxExtract;
		return this;
	}

	/* IEnergyContainerItem */
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (!container.hasTagCompound()) {
			container.setTagCompound(new NBTTagCompound());
		}
		int energy = container.getTagCompound().getInteger("Energy");
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxRecieve, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.getTagCompound().setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.getTagCompound() == null || !container.getTagCompound().hasKey("Energy")) {
			return 0;
		}
		int energy = container.getTagCompound().getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.getTagCompound().setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {

		if (container.getTagCompound() == null || !container.getTagCompound().hasKey("Energy")) {
			return 0;
		}
		return container.getTagCompound().getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return capacity;
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if (worldIn.isRemote && !playerIn.isSneaking()) {
			
		
			// check if we have enough energy
			if(this.getEnergyStored(itemStackIn) >= 50){
				
				playerIn.addChatMessage(new TextComponentString(TextFormatting.DARK_AQUA + "Coordinates: "
						+ TextFormatting.RED + playerIn.getPosition().getX() + TextFormatting.RESET + "x, "
						+ TextFormatting.RED + playerIn.getPosition().getY() + TextFormatting.RESET + "y, "
						+ TextFormatting.RED + playerIn.getPosition().getZ() + TextFormatting.RESET + "z, "));
				this.extractEnergy(itemStackIn, 50, false);	
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
							
			}else if(this.getEnergyStored(itemStackIn) < 50){
				return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
			}
			
			
		}
		
		//debug code remove later
		if (UsefulCompass.isDebug && worldIn.isRemote && playerIn.isSneaking()) {
			
			int stored = this.getEnergyStored(itemStackIn);
			int maxStored = this.getMaxEnergyStored(itemStackIn);
			
			this.receiveEnergy(itemStackIn, (maxStored - stored), false);
		}
		
		
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}

}
