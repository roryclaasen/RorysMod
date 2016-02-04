package net.roryclaasen.rorysmod.container;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
		if (isOnExcludeList(itemstack)) return false;
		if (allowed != null) {
			if (tags) {
				// As I'm only using this slot for the rifle table, this may not work for anything else
				if (itemstack.getIconIndex().getIconName() != allowed.getIconIndex().getIconName()) {
					return false;
				}
			} else {
				if (allowed.getItem() != itemstack.getItem()) return false;
			}
		}
		if (allowedList != null) {
			for (ItemStack allowedItem : allowedList) {
				if (tags) {
					// As I'm only using this slot for the rifle table, this may not work for anything else
					if (itemstack.getIconIndex().getIconName() != allowedItem.getIconIndex().getIconName()) {
						return false;
					}
				} else {
					if (allowedItem.getItem() != itemstack.getItem()) return false;
				}
			}
		}
		return true;
	}

	private boolean isOnExcludeList(ItemStack stack) {
		if (exclude != null) {
			if (ItemStack.areItemStackTagsEqual(stack, exclude)) {
				// As I'm only using this slot for the rifle table, this may not work for anything else
				if (stack.getIconIndex().getIconName() == exclude.getIconIndex().getIconName()) {
					return true;
				}
			}
		}
		if (excludeList != null) {
			for (ItemStack excludeItem : excludeList) {
				if (ItemStack.areItemStackTagsEqual(excludeItem, exclude)) {
					// As I'm only using this slot for the rifle table, this may not work for anything else
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
