package me.roryclaasen.rorysmod.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class OutputSlot extends Slot {

	public OutputSlot(IInventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}
}
