package net.roryclaasen.rorysmod.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.roryclaasen.rorysmod.core.ModItems;
import net.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;

public class ContainerRifleTable extends Container {

	protected TileEntityRifleTable tileEntity;

	private final int NO_CUSTOM_SLOTS = 9;

	public ContainerRifleTable(InventoryPlayer inventoryPlayer, TileEntityRifleTable te) {
		tileEntity = te;

		this.addSlotToContainer(new RestrictedSlot(tileEntity, 0, 8, 8).setAllowedItem(ModItems.rifle));
		// Capacitors
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 1, 75, 63).setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade, 0, 1)));
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 2, 95, 63).setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade, 0, 1)));
		// Coolant
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 3, 79, 5).setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade, 0, 2)));
		// Explosion
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 4, 100, 5).setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade, 0, 6)));
		// Phaser
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 5, 121, 5).setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade, 0, 4)));
		// Lens
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 6, 149, 45).setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade, 0, 3)).setLimit(1));
		// Overclock
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 7, 59, 12).setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade, 0, 5)));

		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		// Player Inventory, Slot 9-35, Slot IDs 8-34
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + NO_CUSTOM_SLOTS, 8 + x * 18, 84 + y * 18));
			}
		}
		// Player Inventory, Slot 0-8, Slot IDs 35-43
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inventoryPlayer, x, 8 + x * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		Slot slotObject = (Slot) inventorySlots.get(slot);
		ItemStack stack = null;
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (slot < tileEntity.getSizeInventory()) {
				if (!this.mergeItemStack(stackInSlot, tileEntity.getSizeInventory(), 36 + tileEntity.getSizeInventory(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(stackInSlot, 0, tileEntity.getSizeInventory(), false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) slotObject.putStack(null);
			else slotObject.onSlotChanged();

			if (stackInSlot.stackSize == stack.stackSize) return null;

			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	public boolean hasLaser() {
		return tileEntity.hasLaser();
	}
}