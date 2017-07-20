/*
 * Copyright 2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmod.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeUtils {

	public static boolean areItemsEqualForCrafting(ItemStack target, ItemStack input) {		
		RMLog.info(target + " /=/ " +input);
		if (target == null && input != null || target == null && input != null) {
			return false;
		} else if (target == null && input == null) {
			return true;
		}

		if (target.getItem() != input.getItem()) {
			return false;
		}

		if (target.getItemDamage() != input.getItemDamage() && target.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public static IRecipe getRecipeFromGrid(InventoryCrafting inv, World world) {
		List<IRecipe> list = new ArrayList<IRecipe>(CraftingManager.getInstance().getRecipeList());

		for (Iterator<IRecipe> iter = list.iterator(); iter.hasNext();) {
			IRecipe recipe = iter.next();

			if (recipe.matches(inv, world)) {
				return recipe;
			}
		}
		return null;
	}
}
