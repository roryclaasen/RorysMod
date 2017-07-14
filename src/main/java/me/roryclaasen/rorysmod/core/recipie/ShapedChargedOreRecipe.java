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
package me.roryclaasen.rorysmod.core.recipie;

import java.util.ArrayList;
import java.util.Iterator;

import codechicken.core.ReflectionManager;
import me.roryclaasen.rorysmod.item.base.ItemBaseEnergyContainer;
import me.roryclaasen.rorysmod.util.RMLog;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedChargedOreRecipe extends ShapedOreRecipe {

	// Added in for future ease of change, but hard coded for now.
	private static final int MAX_CRAFT_GRID_WIDTH = 3;
	private static final int MAX_CRAFT_GRID_HEIGHT = 3;

	private boolean active = true;

	private int width, height;
	private boolean mirrored;
	private Object[] input;

	public ShapedChargedOreRecipe(Block result, Object... recipe) {
		this(new ItemStack(result), recipe);
	}

	public ShapedChargedOreRecipe(Item result, Object... recipe) {
		this(new ItemStack(result), recipe);
	}

	public ShapedChargedOreRecipe(ItemStack result, Object... recipe) {
		super(result, recipe);

		try {
			width = ((Integer) ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, this, 4)).intValue();
			height = ((Integer) ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, this, 5)).intValue();
			mirrored = ((Boolean) ReflectionManager.getField(ShapedOreRecipe.class, Boolean.class, this, 6)).booleanValue();
			input = ((Object[]) ReflectionManager.getField(ShapedOreRecipe.class, Object.class, this, 3));
		} catch (Exception e) {
			RMLog.error("Failed to retrive fields");
			e.printStackTrace();
			active = false;
		}
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if (!active) return super.matches(inv, world);

		for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++) {
			for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y) {
				if (checkMatch(inv, x, y, false)) return true;
				if (mirrored && checkMatch(inv, x, y, true)) return true;
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {
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
				} else if (target == null && slot != null) {
					return false;
				}
			}
		}
		return true;
	}
}