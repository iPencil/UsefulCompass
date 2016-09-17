package com.creepgaming.usefulcompass.recipes;

import com.creepgaming.usefulcompass.items.ItemRegister;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CompassRecipeHandler implements IRecipe {

	private ItemStack resultItem;

	/*
	 * Custom recipe handler for the compass Allows copying one compass with NBT
	 * data to a compass without any.
	 * 
	 * 
	 */

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {

		int i = 0;
		ItemStack itemstack = null;

		for (int j = 0; j < inv.getSizeInventory(); ++j) {
			ItemStack itemstack1 = inv.getStackInSlot(j);

			if (itemstack1 != null) {
				if (itemstack1.getItem() == ItemRegister.itemCompass && itemstack1.hasTagCompound()) {
					if (itemstack != null) {
						return false;
					}

					itemstack = itemstack1;
				} else {
					if (itemstack1.getItem() != ItemRegister.itemCompass && !itemstack1.hasTagCompound()) {
						return false;
					}

					++i;
				}
			}
		}

		return itemstack != null && i > 0;

	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {

		int i = 0;
		ItemStack itemstack = null;

		for (int j = 0; j < inv.getSizeInventory(); ++j) {
			ItemStack itemstack1 = inv.getStackInSlot(j);

			if (itemstack1 != null) {
				if (itemstack1.getItem() == ItemRegister.itemCompass && itemstack1.hasTagCompound()) {
					if (itemstack != null) {
						return null;
					}

					itemstack = itemstack1;
				} else {
					if (itemstack1.getItem() != ItemRegister.itemCompass && !itemstack1.hasTagCompound()) {
						return null;
					}

					++i;
				}
			}
		}

		if (itemstack != null && i >= 1) {
			ItemStack itemstack2 = new ItemStack(ItemRegister.itemCompass, i + 1);
			if (itemstack.hasTagCompound()) {
				itemstack2.setTagCompound(itemstack.getTagCompound());
			}

			return itemstack2;
		} else {
			return null;
		}
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.resultItem;
	}


	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

		for (int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
		}

		return aitemstack;
	}

}
