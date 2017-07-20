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

import me.roryclaasen.rorysmod.util.RecipeUtils;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RoryChargedShapelessRecipe extends RoryShapelessRecipe {

	public RoryChargedShapelessRecipe(Block result, Object... recipe) {
		this(new ItemStack(result), recipe);
	}

	public RoryChargedShapelessRecipe(Item result, Object... recipe) {
		this(new ItemStack(result), recipe);
	}

	public RoryChargedShapelessRecipe(ItemStack result, Object... recipe) {
		super(result, recipe);
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public boolean matches(InventoryCrafting var1, World world) {
		ArrayList<Object> required = new ArrayList<Object>(input);

		for (int x = 0; x < var1.getSizeInventory(); x++) {
			ItemStack slot = var1.getStackInSlot(x);

			if (slot != null) {
				boolean inRecipe = false;
				Iterator<Object> req = required.iterator();

				while (req.hasNext()) {
					boolean match = false;

					Object next = req.next();

					if (next instanceof ItemStack) {
						match = RecipeUtils.areItemsEqualForCrafting((ItemStack) next, slot);
					} else if (next instanceof ArrayList) {
						Iterator<ItemStack> itr = ((ArrayList<ItemStack>) next).iterator();
						while (itr.hasNext() && !match) {
							ItemStack stack = itr.next();
							match = RecipeUtils.areItemsEqualForCrafting(stack, slot);
						}
					}
					if (match) {
						inRecipe = true;
						required.remove(next);
						break;
					}
				}
				if (!inRecipe) {
					return false;
				}
			}
		}

		return required.isEmpty();
	}
}