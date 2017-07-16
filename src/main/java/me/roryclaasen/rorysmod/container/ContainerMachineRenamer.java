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
package me.roryclaasen.rorysmod.container;

import me.roryclaasen.rorysmod.entity.tile.TileEntityRenamer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineRenamer extends Container {
	protected TileEntityRenamer tileEntity;

	public ContainerMachineRenamer(TileEntityRenamer tileEntity, EntityPlayer player) {
		this.tileEntity = tileEntity;

		// Custom Storage
		addSlotToContainer(new Slot(tileEntity, 0, 26, 44));
		addSlotToContainer(new OutputSlot(tileEntity, 1, 134, 44));

		bindPlayerInventory(player.inventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inventoryPlayer, x, 8 + x * 18, 142));
		}
	}
}
