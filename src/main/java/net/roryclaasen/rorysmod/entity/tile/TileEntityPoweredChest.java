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

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.container.ContainerPoweredChest;
import net.roryclaasen.rorysmod.core.ModBlocks;

public class TileEntityPoweredChest extends TileEntity implements IInventory {

	private int ticksSinceSync = -1;
	public float prevLidAngle;
	public float lidAngle;
	private int numUsingPlayers;
	private byte facing;

	private ItemStack[] items = new ItemStack[27];

	public int getFacing() {
		return this.facing;
	}

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

	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity() {
		if (worldObj != null && !this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0) {
			this.numUsingPlayers = 0;
			float var1 = 5.0F;
			List<EntityPlayer> var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - var1, yCoord - var1, zCoord - var1, xCoord + 1 + var1, yCoord + 1 + var1, zCoord + 1 + var1));

			for (EntityPlayer var4 : var2) {
				if (var4.openContainer instanceof ContainerPoweredChest) {
					++this.numUsingPlayers;
				}
			}
		}

		if (worldObj != null && !worldObj.isRemote && ticksSinceSync < 0) {
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.poweredChest, 3, ((numUsingPlayers << 3) & 0xF8) | (facing & 0x7));
		}

		this.ticksSinceSync++;
		prevLidAngle = lidAngle;
		float f = 0.1F;
		if (numUsingPlayers > 0 && lidAngle == 0.0F) {
			double d = xCoord + 0.5D;
			double d1 = zCoord + 0.5D;
			worldObj.playSoundEffect(d, yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F) {
			float f1 = lidAngle;
			if (numUsingPlayers > 0) {
				lidAngle += f;
			} else {
				lidAngle -= f;
			}
			if (lidAngle > 1.0F) {
				lidAngle = 1.0F;
			}
			float f2 = 0.5F;
			if (lidAngle < f2 && f1 >= f2) {
				double d2 = xCoord + 0.5D;
				double d3 = zCoord + 0.5D;
				worldObj.playSoundEffect(d2, yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if (lidAngle < 0.0F) {
				lidAngle = 0.0F;
			}
		}
	}

	@Override
	public boolean receiveClientEvent(int i, int j) {
		if (i == 1) {
			numUsingPlayers = j;
		} else if (i == 2) {
			facing = (byte) j;
		} else if (i == 3) {
			facing = (byte) (j & 0x7);
			numUsingPlayers = (j & 0xF8) >> 3;
		}
		return true;
	}

	@Override
	public void openInventory() {
		if (worldObj == null) {
			return;
		}
		numUsingPlayers++;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.poweredChest, 1, numUsingPlayers);
	}

	@Override
	public void closeInventory() {
		if (worldObj == null) {
			return;
		}
		numUsingPlayers--;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.poweredChest, 1, numUsingPlayers);
	}

	public void setFacing(byte facing2) {
		this.facing = facing2;
	}

	public void rotateAround() {
		facing++;
		if (facing > ForgeDirection.EAST.ordinal()) {
			facing = (byte) ForgeDirection.NORTH.ordinal();
		}
		setFacing(facing);
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.poweredChest, 2, facing);
	}

	public void wasPlaced(EntityLivingBase entityliving, ItemStack itemStack) {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}
}