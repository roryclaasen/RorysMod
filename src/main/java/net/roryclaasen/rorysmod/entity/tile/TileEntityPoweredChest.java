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
package net.roryclaasen.rorysmod.entity.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.core.ModBlocks;

public class TileEntityPoweredChest extends TileEntity implements IInventory {

	public float field_145972_a;
	public float field_145975_i;
	public int field_145973_j;
	private int field_145974_k;

	private ItemStack[] items = new ItemStack[27];

	public int getSizeInventory() {
		return items.length;
	}

	public ItemStack getStackInSlot(int slot) {
		return items[slot];
	}

	public ItemStack decrStackSize(int slot, int amount) {
		if (items[slot] != null) {
			ItemStack itemstack;
			if (items[slot].stackSize == amount) {
				itemstack = items[slot];
				items[slot] = null;
				markDirty();
				return itemstack;
			} else {
				itemstack = items[slot].splitStack(amount);
				if (items[slot].stackSize == 0) items[slot] = null;
				markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		if (items[slot] != null) {
			ItemStack itemstack = items[slot];
			items[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int slot, ItemStack stack) {
		items[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}

		markDirty();
	}

	public String getInventoryName() {
		return RorysMod.GUIS.CHEST_POWERED.getName();
	}

	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);

		items = new ItemStack[getSizeInventory()];
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound comp = list.getCompoundTagAt(i);
			int j = comp.getByte("Slot") & 255;
			if (j >= 0 && j < items.length) {
				items[j] = ItemStack.loadItemStackFromNBT(comp);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < items.length; ++i) {
			if (items[i] != null) {
				NBTTagCompound comp = new NBTTagCompound();
				comp.setByte("Slot", (byte) i);
				items[i].writeToNBT(comp);
				list.appendTag(comp);
			}
		}

		nbt.setTag("Items", list);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) == this) {
			if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				if (player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D) {
					return true;
				}
			} else {
				if (worldObj.isRemote) {
					player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.chest.state.locked")));
				}
			}
		}
		return false;
	}

	public void updateEntity() {
		super.updateEntity();
		if (++this.field_145974_k % 20 * 4 == 0) {
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, ModBlocks.poweredChest, 1, this.field_145973_j);
		}
		this.field_145975_i = this.field_145972_a;
		float f = 0.1F;
		double d1;
		if (this.field_145973_j > 0 && this.field_145972_a == 0.0F) {
			double d0 = (double) this.xCoord + 0.5D;
			d1 = (double) this.zCoord + 0.5D;
			this.worldObj.playSoundEffect(d0, (double) this.yCoord + 0.5D, d1, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if (this.field_145973_j == 0 && this.field_145972_a > 0.0F || this.field_145973_j > 0 && this.field_145972_a < 1.0F) {
			float f2 = this.field_145972_a;
			if (this.field_145973_j > 0) {
				this.field_145972_a += f;
			} else {
				this.field_145972_a -= f;
			}
			if (this.field_145972_a > 1.0F) {
				this.field_145972_a = 1.0F;
			}
			float f1 = 0.5F;
			if (this.field_145972_a < f1 && f2 >= f1) {
				d1 = (double) this.xCoord + 0.5D;
				double d2 = (double) this.zCoord + 0.5D;
				this.worldObj.playSoundEffect(d1, (double) this.yCoord + 0.5D, d2, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if (this.field_145972_a < 0.0F) {
				this.field_145972_a = 0.0F;
			}
		}
	}

	/**
	 * Called when a client event is received with the event number and argument, see World.sendClientEvent
	 */
	public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_) {
		if (p_145842_1_ == 1) {
			this.field_145973_j = p_145842_2_;
			return true;
		} else {
			return super.receiveClientEvent(p_145842_1_, p_145842_2_);
		}
	}

	/**
	 * invalidates a tile entity
	 */
	public void invalidate() {
		this.updateContainingBlockInfo();
		super.invalidate();
	}

	public void func_145969_a() {
		++this.field_145973_j;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, ModBlocks.poweredChest, 1, this.field_145973_j);
	}

	public void func_145970_b() {
		--this.field_145973_j;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, ModBlocks.poweredChest, 1, this.field_145973_j);
	}

	public boolean func_145971_a(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}
}