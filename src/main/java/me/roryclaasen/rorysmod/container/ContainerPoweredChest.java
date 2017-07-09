/*
Copyright 2016-2017 Rory Claasen

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
package me.roryclaasen.rorysmod.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import me.roryclaasen.rorysmod.entity.tile.TileEntityPoweredChest;

public class ContainerPoweredChest extends Container {

	private TileEntityPoweredChest tile;

	private int slotID = 0;

	public ContainerPoweredChest(TileEntityPoweredChest te, EntityPlayer player) {
		this.tile = te;
		// Custom Storage
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(te, slotID++, 8 + j * 18, 17 + i * 18));
			}
		}
		// Inventory
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		// Hotbar
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotRaw) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotRaw);
		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if (slotRaw < 3 * 9) {
				if (!mergeItemStack(stackInSlot, 3 * 9, inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 0, 3 * 9, false)) {
				return null;
			}
			if (stackInSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}
		return stack;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		tile.closeInventory();

		if (!tile.getWorldObj().isRemote) {
			tile.getWorldObj().playSoundEffect(tile.xCoord, tile.yCoord, tile.zCoord, "random.chestclosed", 0.5F, tile.getWorldObj().rand.nextFloat() * 0.1F + 0.9F);
		}
		super.onContainerClosed(player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}
}
