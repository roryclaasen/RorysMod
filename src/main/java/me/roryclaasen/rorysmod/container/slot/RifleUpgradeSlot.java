package me.roryclaasen.rorysmod.container.slot;

import java.util.List;

import me.roryclaasen.rorysmod.block.tile.TileEntityRifleTable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class RifleUpgradeSlot extends RestrictedSlot {

	public RifleUpgradeSlot(IInventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
	}

	public RifleUpgradeSlot(IInventory inventory, int par2, int par3, int par4, List<ItemStack> items) {
		super(inventory, par2, par3, par4, items);
	}

	public RifleUpgradeSlot(IInventory inventory, int par2, int par3, int par4, ItemStack item) {
		super(inventory, par2, par3, par4, item);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (inventory instanceof TileEntityRifleTable) {
			if (((TileEntityRifleTable) inventory).hasLaser()) {
				return super.isItemValid(itemstack);
			}
		}
		return false;
	}
}
