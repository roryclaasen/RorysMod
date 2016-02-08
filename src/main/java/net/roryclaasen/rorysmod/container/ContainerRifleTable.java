/*
Copyright 2016 Rory Claasen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package net.roryclaasen.rorysmod.container;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.roryclaasen.rorysmod.core.ModItems;
import net.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;

public class ContainerRifleTable extends Container {

	protected TileEntityRifleTable tileEntity;

	public static final int NO_CUSTOM_SLOTS = 9;

	public ContainerRifleTable(InventoryPlayer inventoryPlayer, TileEntityRifleTable te) {
		tileEntity = te;
		List<ItemStack> rifles = new ArrayList<ItemStack>();
		rifles.add(new ItemStack(ModItems.rifle1));
		rifles.add(new ItemStack(ModItems.rifle2));
		rifles.add(new ItemStack(ModItems.rifle3));
		rifles.add(new ItemStack(ModItems.rifle4));
		rifles.add(new ItemStack(ModItems.rifle5));
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 0, 8, 8).setAllowedItemStack(rifles));
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 1, 134, 7).setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade, 1, 3)).useTags());
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 2, 152, 7));
		// cords on image 134 25
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 2; x++) {
				RestrictedSlot slot = new RestrictedSlot(tileEntity, 3 + (x + y * 2), 134 + (18 * x), 25 + (18 * y));
				slot.setAllowedItemStack(new ItemStack(ModItems.rifleUpgrade));
				slot.exclude(new ItemStack(ModItems.rifleUpgrade, 1, 3));
				this.addSlotToContainer(slot);
			}
		}
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		// Player Inventory, Slot 9-35, Slot IDs 9-35
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		// Player Inventory, Slot 0-8, Slot IDs 36-44
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
			} else {
				if (!this.mergeItemStack(stackInSlot, 0, tileEntity.getSizeInventory(), false)) {
				}
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