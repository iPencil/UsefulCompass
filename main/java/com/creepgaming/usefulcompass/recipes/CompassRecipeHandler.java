package com.creepgaming.usefulcompass.recipes;

import java.util.List;

import com.creepgaming.usefulcompass.items.ItemCompass;
import com.google.common.collect.Lists;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CompassRecipeHandler implements IRecipe{

	
	private ItemStack resultItem;


	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack itemstack = null;
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() instanceof ItemCompass)
                {


                    list.add(itemstack1);
                }else{
                	return false;
                }
            }
        }

        return itemstack != null && !list.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.resultItem.copy();
	}

	@Override
	public int getRecipeSize() {
        return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
        return this.resultItem;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }

        return aitemstack;
	}
	
	
	
	
}
