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

import java.util.ArrayList;
import java.util.List;

import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRifleTable extends Container {

	protected TileEntityRifleTable tileEntity;

	public static final int NO_CUSTOM_SLOTS = 8;

	public ContainerRifleTable(InventoryPlayer inventoryPlayer, TileEntityRifleTable te) {
		this.tileEntity = te;
		List<ItemStack> rifles = new ArrayList<ItemStack>();
		rifles.add(new ItemStack(RorysMod.items.rifle1));
		rifles.add(new ItemStack(RorysMod.items.rifle2));
		rifles.add(new ItemStack(RorysMod.items.rifle3));
		rifles.add(new ItemStack(RorysMod.items.rifle4));
		rifles.add(new ItemStack(RorysMod.items.rifle5));

		this.addSlotToContainer(new RestrictedSlot(tileEntity, 0, 8, 8).setAllowedItemStack(rifles));
		this.addSlotToContainer(new RestrictedSlot(tileEntity, 1, 134, 7).setAllowedItemStack(new ItemStack(RorysMod.items.rifleUpgrade, 1, 3)).setLimit(1).useTags());

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 2; x++) {
				RestrictedSlot slot = new RestrictedSlot(tileEntity, 2 + (x + y * 2), 134 + (18 * x), 25 + (18 * y));
				slot.setAllowedItemStack(new ItemStack(RorysMod.items.rifleUpgrade));
				slot.exclude(new ItemStack(RorysMod.items.rifleUpgrade, 1, 3));
				this.addSlotToContainer(slot);
			}
		}
		bindPlayerInventory(inventoryPlayer);
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

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}

	public boolean hasLaser() {
		return tileEntity.hasLaser();
	}
}
