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
package me.roryclaasen.rorysmod.core.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import me.roryclaasen.rorysmod.item.base.ItemBaseEnergyContainer;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class RoryChargedShapedRecipe extends RoryShapedRecipe {

	public RoryChargedShapedRecipe(Block result, Object... recipe) {
		this(new ItemStack(result), recipe);
	}

	public RoryChargedShapedRecipe(Item result, Object... recipe) {
		this(new ItemStack(result), recipe);
	}

	public RoryChargedShapedRecipe(ItemStack result, Object... recipe) {
		super(result, recipe);
	}

	RoryChargedShapedRecipe(ShapedRecipes recipe, Map<ItemStack, String> replacements) {
		super(recipe, replacements);
	}

	@SuppressWarnings("unchecked")
	protected boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {
		for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++) {
			for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++) {
				int subX = x - startX;
				int subY = y - startY;
				Object target = null;

				if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
					if (mirror) target = input[width - subX - 1 + subY * width];
					else target = input[subX + subY * width];
				}

				ItemStack slot = inv.getStackInRowAndColumn(x, y);

				if (slot != null && slot.getItem() != null && slot.getItem() instanceof ItemBaseEnergyContainer) {
					if (slot.getItemDamage() == 100) return false;
					int energy = 0;
					if (slot.stackTagCompound != null) {
						if (slot.stackTagCompound.hasKey("Energy")) energy = slot.stackTagCompound.getInteger("Energy");
						else if (slot.stackTagCompound.hasKey("energy")) energy = slot.stackTagCompound.getInteger("energy");
					}
					if (slot.getItemDamage() == OreDictionary.WILDCARD_VALUE) energy = 100;
					if (energy <= 0) return false;
				}

				if (target instanceof ItemStack) {
					if (!OreDictionary.itemMatches((ItemStack) target, slot, false)) return false;
				} else if (target instanceof ArrayList) {
					boolean matched = false;
					Iterator<ItemStack> itr = ((ArrayList<ItemStack>) target).iterator();
					while (itr.hasNext() && !matched) {
						matched = OreDictionary.itemMatches(itr.next(), slot, false);
					}
					if (!matched) return false;
				} else if (target == null && slot != null) return false;
			}
		}
		return true;
	}
}