package net.roryclaasen.rorysmod.container;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RestrictedSlot extends Slot {

	private List<ItemStack> allowedList;
	private ItemStack allowed;

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
		return this;
	}

	public RestrictedSlot setAllowedItemStack(List<ItemStack> items) {
		this.allowedList = items;
		return this;
	}

	public RestrictedSlot setAllowedItem(Item item) {
		this.allowed = new ItemStack(item);
		return this;
	}

	public RestrictedSlot setAllowedItem(List<Item> items) {
		this.allowedList.clear();
		for (Item item : items) {
			this.allowedList.add(new ItemStack(item));
		}
		return this;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (allowed != null) return allowed.isItemEqual(itemstack);
		if (allowed != null) return ItemStack.areItemStackTagsEqual(itemstack, allowed);
		if (allowedList != null) {
			for (ItemStack item : allowedList) {
				if (ItemStack.areItemStackTagsEqual(itemstack, item)) return true;
			}
			return false;
		}
		return true;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
