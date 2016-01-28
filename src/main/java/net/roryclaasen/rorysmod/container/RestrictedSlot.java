package net.roryclaasen.rorysmod.container;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RestrictedSlot extends Slot {

	private List<Item> allowedList;
	private Item allowed;

	public RestrictedSlot(IInventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
	}

	public RestrictedSlot(IInventory inventory, int par2, int par3, int par4, List<Item> items) {
		this(inventory, par2, par3, par4);
		setAllowed(items);
	}

	public RestrictedSlot(IInventory inventory, int par2, int par3, int par4, Item item) {
		super(inventory, par2, par3, par4);
		setAllowed(item);
	}

	public RestrictedSlot setAllowed(Item item) {
		this.allowed = item;
		return this;
	}

	public RestrictedSlot setAllowed(List<Item> items) {
		this.allowedList = items;
		return this;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (allowed != null) return itemstack.isItemEqual(new ItemStack(allowed));
		if (allowedList != null) {
			for (Item item : allowedList) {
				if (itemstack.isItemEqual(new ItemStack(item))) return true;
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
