/*
 * Copyright 2016-2017 Rory Claasen
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
package me.roryclaasen.rorysmod.container;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * As I'm only using this slot for the rifle table, this may not work for anything else
 */
public class RestrictedSlot extends Slot {

	private List<ItemStack> allowedList, excludeList;
	private ItemStack allowed, exclude;
	private int limit = 64;
	private boolean tags = false;

	public RestrictedSlot(IInventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
	}

	public RestrictedSlot(IInventory inventory, int par2, int par3, int par4, List<ItemStack> items) {
		this(inventory, par2, par3, par4);
		setAllowedItemStack(items);
	}

	public RestrictedSlot(IInventory inventory, int par2, int par3, int par4, ItemStack item) {
		super(inventory, par2, par3, par4);
		setAllowedItemStack(item);
	}

	public RestrictedSlot setAllowedItemStack(ItemStack item) {
		this.allowed = item;
		this.limit = item.getMaxStackSize();
		return this;
	}

	public RestrictedSlot setAllowedItemStack(List<ItemStack> items) {
		this.allowedList = items;
		return this;
	}

	public RestrictedSlot setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	public RestrictedSlot useTags() {
		tags = true;
		return this;
	}

	public RestrictedSlot setUseTags(boolean tags) {
		this.tags = tags;
		return this;
	}

	public RestrictedSlot exclude(ItemStack itemstack) {
		this.exclude = itemstack;
		return this;
	}

	public RestrictedSlot excludeList(List<ItemStack> itemstacks) {
		this.excludeList = itemstacks;
		return this;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		ItemStack current = getStack();
		if (current != null) {
			if (current.stackSize >= limit) return false;
		}
		if (isOnExcludeList(itemstack)) return false;
		if (allowed != null) {
			if (tags) {
				if (itemstack.getIconIndex().getIconName() == allowed.getIconIndex().getIconName()) return true;
			} else {
				if (allowed.getItem() == itemstack.getItem()) return true;
			}
		}
		if (allowedList != null) {
			for (ItemStack allowedItem : allowedList) {
				if (tags) {
					if (itemstack.getIconIndex().getIconName() == allowedItem.getIconIndex().getIconName()) return true;
				} else {
					if (allowedItem.getItem() == itemstack.getItem()) return true;
				}
			}
		}
		return false;
	}

	private boolean isOnExcludeList(ItemStack stack) {
		if (exclude != null) {
			if (ItemStack.areItemStackTagsEqual(stack, exclude)) {
				if (stack.getIconIndex().getIconName() == exclude.getIconIndex().getIconName()) {
					return true;
				}
			}
		}
		if (excludeList != null) {
			for (ItemStack excludeItem : excludeList) {
				if (ItemStack.areItemStackTagsEqual(excludeItem, exclude)) {
					if (stack.getIconIndex().getIconName() == excludeItem.getIconIndex().getIconName()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public int getSlotStackLimit() {
		return limit;
	}

	public boolean getUseTags() {
		return tags;
	}
}