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
package me.roryclaasen.rorysmod.entity.tile;

import java.awt.Color;

import me.roryclaasen.rorysmod.container.ContainerRifleTable;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.gui.GuiRifleTable;
import me.roryclaasen.rorysmod.item.ItemRifleUpgrade;
import me.roryclaasen.rorysmod.item.tools.ItemRifle;
import me.roryclaasen.rorysmod.util.ColorUtils;
import me.roryclaasen.rorysmod.util.NBTLaser;
import me.roryclaasen.rorysmod.util.RMLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileEntityRifleTable extends TileEntity implements IInventory {

	private ItemStack[] inv;

	private Color laserColor;

	private GuiRifleTable gui;

	public TileEntityRifleTable() {
		inv = new ItemStack[ContainerRifleTable.NO_CUSTOM_SLOTS];
		laserColor = Color.RED;
	}

	public void setGuiRifleTable(GuiRifleTable gui) {
		this.gui = gui;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		writeToLaser();
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if (stack != null) {
			if (stack.stackSize > getInventoryStackLimit()) {
				stack.stackSize = getInventoryStackLimit();
			}
			if (stack.getItem() instanceof ItemRifle) {
				NBTLaser data = new NBTLaser(stack.stackTagCompound);
				if (data.hasLens()) {
					inv[1] = new ItemStack(RorysMod.items.rifleUpgrade, 1, 3);
					if (gui != null) {
						laserColor = ColorUtils.getColorFromIntArray(data.getColor());
						gui.setColorSlider(laserColor);
					}
				}
				for (int i = 2; i < ContainerRifleTable.NO_CUSTOM_SLOTS; i++) {
					int[] upgrade = data.getSlot(i - 2);
					if (upgrade[0] >= 0) inv[i] = new ItemStack(RorysMod.items.rifleUpgrade, upgrade[1], upgrade[0] + 1);
				}
			}
		}
		writeToLaser();
		if (!hasLaser()) {
			for (int i = 0; i < inv.length; i++) {
				inv[i] = null;
			}
		}
	}

	public void writeToLaser() {
		if (hasLaser()) {
			NBTLaser data = new NBTLaser(inv[0].stackTagCompound);
			
			data.setLens(inv[1] != null);
			for (int i = 2; i < ContainerRifleTable.NO_CUSTOM_SLOTS; i++) {
				ItemStack stack = inv[i];
				if (stack != null) {
					if (stack.getItem() instanceof ItemRifleUpgrade) {
						RMLog.info(stack.getDisplayName() + ">" + stack.getItemDamage());
						data.setSlot(i - 2, stack.getItemDamage() - 1, stack.stackSize);
					}
				} else data.setSlot(i - 2, -1, 0);
			}
			data.setColor(ColorUtils.getIntArrayFromColor(laserColor));
			inv[0].stackTagCompound = data.getTag();
			((ItemRifle) inv[0].getItem()).updateNBT(inv[0]);
		}
	}

	public boolean hasLaser() {
		if (inv[0] == null) return false;
		if (inv[0].getItem() == null) return false;
		return inv[0].getItem() instanceof ItemRifle;
	}

	public ItemStack getLaser() {
		if (!hasLaser()) return null;
		return inv[0];
	}

	public boolean hasLens() {
		if (!hasLaser()) return false;
		NBTLaser data = new NBTLaser(inv[0].stackTagCompound);
		return data.hasLens();
	}

	public void setColor(Color color) {
		laserColor = color;
		writeToLaser();
	}

	@Override
	public String getInventoryName() {
		return RorysMod.GUIS.RILE_TABLE.getName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (hasLaser()) return true;
		if (slot == 0) return true;
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);

		inv = new ItemStack[getSizeInventory()];
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound comp = list.getCompoundTagAt(i);
			int j = comp.getByte("Slot") & 255;
			if (j >= 0 && j < inv.length) {
				inv[j] = ItemStack.loadItemStackFromNBT(comp);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < inv.length; ++i) {
			if (inv[i] != null) {
				NBTTagCompound comp = new NBTTagCompound();
				comp.setByte("Slot", (byte) i);
				inv[i].writeToNBT(comp);
				list.appendTag(comp);
			}
		}

		nbt.setTag("Items", list);
	}
}
