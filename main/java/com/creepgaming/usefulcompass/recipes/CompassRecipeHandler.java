package com.creepgaming.usefulcompass.recipes;

import com.creepgaming.usefulcompass.items.ItemRegister;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CompassRecipeHandler implements IRecipe {

	private ItemStack resultItem;

	/*
	 * This class is currently very crude and was made as a proof on concept.
	 * Needs to be rewritten to allow for truly shapeless recipe.
	 * 
	 * 
	 */

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {

		/*
		 * Currently takes the first and second slot, checks if those contain
		 * the right items and then returns true.
		 */

		if (inv.getStackInSlot(0) != null && inv.getStackInSlot(0).getItem() == ItemRegister.itemCompass
				&& inv.getStackInSlot(1) != null && inv.getStackInSlot(1).getItem() == ItemRegister.itemCompass
				&& !inv.getStackInSlot(1).hasTagCompound()) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {

		/*
		 * Checks for nbt tags, and if so copies the current tag to a new
		 * itemstack
		 * 
		 */

		if (inv.getStackInSlot(0).hasTagCompound() == true) {

			NBTTagCompound tag = inv.getStackInSlot(0).getTagCompound();

			ItemStack returnStack = new ItemStack(ItemRegister.itemCompass, 2);

			returnStack.setTagCompound(tag);

			this.resultItem = returnStack;
			return returnStack;

		}

		return null;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.resultItem;
	}

	/*
	 * No idea what this does exactly, needs rewriting, looks complicated. Gotta take a look at it at some point. It works for now so I haven't bothered yet.
	 * 
	 */
	
	
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
