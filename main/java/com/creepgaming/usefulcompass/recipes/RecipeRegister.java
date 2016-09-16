package com.creepgaming.usefulcompass.recipes;

import com.creepgaming.usefulcompass.items.ItemRegister;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeRegister {

	public static void registerRecipes(){
		
		
		 IRecipe woodCompass = new ShapedOreRecipe(new ItemStack(ItemRegister.itemCompass), new Object[] {
		            "W.W",
		            ".I.",
		            "W.W",
		            'I', Items.IRON_INGOT,   
		            'W', "plankWood",  
		    });
		
		
	    GameRegistry.addRecipe(woodCompass);
	    
	}
	
	
}
