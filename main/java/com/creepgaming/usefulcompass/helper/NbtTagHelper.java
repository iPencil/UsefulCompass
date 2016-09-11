package com.creepgaming.usefulcompass.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

public class NbtTagHelper {

	/*
	 * Shorter and clearer check for NBT Tags
	 */

	public static boolean hasTagCompound(ItemStack stack){
		if(stack.hasTagCompound()){
			return true;
		}
		return false;
	}
	
	
	public static boolean hasAnyTag(ItemStack stack) {
		if (hasTagCompound(stack)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean hasIndexTag(ItemStack stack) {
		if (hasAnyTag(stack) && stack.getTagCompound().hasKey("Index")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean hasSelectorTag(ItemStack stack) {
		if (hasAnyTag(stack) && stack.getTagCompound().hasKey("Selector")) {
			return true;
		} else {
			return false;
		}
	}

	private static int getIndexFromNbt(ItemStack stack) {

		return stack.getTagCompound().getInteger("Index");
	}

	private static int getSelectorFromNbt(ItemStack stack) {

		return stack.getTagCompound().getInteger("Selector");
	}

	public static boolean hasRequiredTags(ItemStack stack) {
		if (hasIndexTag(stack) && hasSelectorTag(stack)) {
			return true;
		}
		return false;
	}

	/*
	 * Calculates the correct next selector based on index. Rolls over if Selector
	 * is bigger than Index
	 */

	private static int calculateSelector(ItemStack stack) {
		int index = getIndexFromNbt(stack);
		int selector = getSelectorFromNbt(stack);

		if (index == 0) {
			return index;

		} else if (selector < index) {
			return selector +1;

		} else if (selector == index) {
			return 0;
		}else{

		return 0;
		}
	}

	/*
	 * Replaces the selector with the one previously calculated
	 */
	
	public static void iterateSelector(ItemStack stack, EntityPlayer p) {
		if (hasRequiredTags(stack)) {
			int selector = calculateSelector(stack);
			NBTTagCompound tag = stack.getTagCompound();
			tag.removeTag("Selector");
			tag.setInteger("Selector", selector);
			stack.setTagCompound(tag);
			ChatHelper.notifyLocationSelect(p, "loc" + selector);
		}
	}

	/*
	 * Saves Location to an Nbt Tag based on Index If no Index is present the
	 * item has no stored locations or hasn't been used yet A new index is then
	 * created at 0
	 * 
	 * If Index is already present, it is removed and replaced with the new
	 * Index++
	 */

	public static void saveLocationToNbt(ItemStack stack, int x, int z, EntityPlayer p) {
		if (!hasRequiredTags(stack)) {
			int[] locArray = new int[] { x, z };
			String location = "loc" + 0;
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Index", 0);
			tag.setInteger("Selector", 0);
			tag.setIntArray(location, locArray);
			stack.setTagCompound(tag);
			ChatHelper.notifyLocationCreate(p, location);
		} else {
			int[] locArray = new int[] {x, z};
			int index = getIndexFromNbt(stack);
			int newIndex = index + 1;
			String location = "loc" + newIndex;
			NBTTagCompound tag = stack.getTagCompound();
			tag.removeTag("Index");
			tag.setInteger("Index", index+1);
			tag.removeTag("Selector");
			tag.setInteger("Selector", index+1);
			tag.setIntArray(location, locArray);
			stack.setTagCompound(tag);
			ChatHelper.notifyLocationCreate(p, location);
			p.addChatComponentMessage(new TextComponentString("loc: " + locArray[0] + "x, " +locArray[1] + " z"));
		}

	}

	/*
	 * Allows deleting a Tag by it's Key
	 */

	public static ItemStack clearNbtTagByKey(ItemStack stack, String key) {
		if (hasAnyTag(stack) && stack.getTagCompound().hasKey(key)) {
			stack.getTagCompound().removeTag(key);
			return (stack);
		}
		return stack;
	}

	
	/*
	 * Methods to get X and Z coordinates from raw location Array
	 */

	public static int getLocationX(ItemStack stack) {
		if (hasRequiredTags(stack)){
		
			int[] locArray = stack.getTagCompound().getIntArray("loc" + stack.getTagCompound().getInteger("Selector"));
			
			if (locArray.length == 2){
				
				
			return locArray[0];
			}
			return 0;
		}
		return 0;
	}

	public static int getLocationZ(ItemStack stack) {
		if (hasRequiredTags(stack)){
			
			
			
			int[] locArray = stack.getTagCompound().getIntArray("loc" + stack.getTagCompound().getInteger("Selector"));
			
			if (locArray.length == 2){
				
			
			return locArray[1];
			}
			return 0;
		}
		
		return 0;
	}

}
