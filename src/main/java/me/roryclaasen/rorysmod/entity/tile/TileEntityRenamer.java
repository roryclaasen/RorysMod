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
package me.roryclaasen.rorysmod.entity.tile;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.tileentity.ITileInfo;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.network.PacketDispatcher;
import me.roryclaasen.rorysmod.core.network.message.SyncTileEntityData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRenamer extends TileEntityBase implements ISidedInventory, IEnergyReceiver, ITileInfo {

	public static final int ENERGY_STORAGE = 2000;
	private ItemStack[] inv;

	private EnergyStorage energy = new EnergyStorage(ENERGY_STORAGE);

	private int tickSinceLastRename;

	private String renamingName;

	public TileEntityRenamer() {
		inv = new ItemStack[2];
		renamingName = "";
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
	public ItemStack decrStackSize(int slot, int amount) {
		if (inv[slot] != null) {
			ItemStack itemstack;
			if (inv[slot].stackSize == amount) {
				itemstack = inv[slot];
				inv[slot] = null;
				this.markDirty();
				return itemstack;
			} else {
				itemstack = inv[slot].splitStack(amount);
				if (inv[slot].stackSize == 0) inv[slot] = null;
				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
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
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return RorysMod.GUIS.MACHINE_RENAMER.getName();
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
		if (slot == 1) return false;
		ItemStack input = inv[0];
		if (input == null) return true;
		if (input.isItemEqual(stack)) return true;
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		if (slot == 0) return false;
		return true;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection direction) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection direction) {
		return energy.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection direction) {
		return energy.getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(ForgeDirection direction, int amount, boolean simulate) {
		return energy.receiveEnergy(amount, simulate);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote) return;

		ItemStack input = inv[0];

		if (StringUtils.isBlank(renamingName)) return;

		if (input != null) {
			if (energy.getEnergyStored() >= 40 + renamingName.length()) {
				energy.extractEnergy(40 + renamingName.length(), false);
			} else return;

			tickSinceLastRename++;
			if (tickSinceLastRename < 10) return;
			ItemStack copy = input.copy();
			copy.stackSize = 1;
			copy.setStackDisplayName(renamingName);

			ItemStack result = inv[1];
			if (result == null) {
				inv[1] = copy;

				--input.stackSize;
			} else {
				if (isItemEqual(result, copy)) {
					if (result.stackSize < 64) {
						++result.stackSize;

						--input.stackSize;
					}
				}
			}
			tickSinceLastRename = 0;
			if (input.stackSize <= 0) {
				inv[0] = null;
			}
		}
	}

	private boolean isItemEqual(ItemStack item1, ItemStack item2) {
		if (item1.isItemEqual(item2)) {
			if (item1.getDisplayName().equals(item2.getDisplayName())) return true;
		}
		return false;
	}

	public void setRenameName(String name) {
		this.renamingName = name;
		this.sync();
	}

	public String getCustomName() {
		return renamingName;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energy.readFromNBT(nbt);

		renamingName = nbt.getString("settingName");

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
		energy.writeToNBT(nbt);

		nbt.setString("settingName", renamingName);

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

		this.markDirty();
	}

	@Override
	public void getTileInfo(List<IChatComponent> list, ForgeDirection direction, EntityPlayer player, boolean simulate) {
		list.add(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.energyStored") + " : " + energy.getEnergyStored() + "/" + energy.getMaxEnergyStored() + " RF"));
		list.add(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.custName") + " : " + renamingName));
	}

	@Override
	public void sync() {
		super.sync();

		NBTTagCompound tagCompount = new NBTTagCompound();
		writeToNBT(tagCompount);
		PacketDispatcher.sendToServer(new SyncTileEntityData(tagCompount));
	}

	public EnergyStorage getEnergy() {
		return energy;
	}
}
