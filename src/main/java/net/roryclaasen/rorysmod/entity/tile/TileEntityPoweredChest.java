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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.core.ModBlocks;

public class TileEntityPoweredChest extends TileEntity implements IInventory {

	public float prevLidAngle;
	public float lidAngle;
	public int numUsingPlayers;
	
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

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, blockMetadata, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
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
				if (!worldObj.isRemote) {
					player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.chest.state.locked")));
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	@Override
	public void updateEntity() {
		this.prevLidAngle = this.lidAngle;
		float f = 0.15F;
		double d2;

		if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F) {
			this.worldObj.playSoundEffect(xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.6f) {
			float f1 = this.lidAngle;

			if (this.numUsingPlayers > 0) {
				this.lidAngle += f;
			} else {
				this.lidAngle -= f;
			}

			if (this.lidAngle > 1.6F) {
				this.lidAngle = 1.6F;
			}

			float f2 = 0.5F;

			if (this.lidAngle < f2 && f1 >= f2) {
				this.worldObj.playSoundEffect(xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
	}

	@Override
	public boolean receiveClientEvent(int id, int data) {
		if (id == 1) {
			this.numUsingPlayers = data;
			return true;
		} else {
			return super.receiveClientEvent(id, data);
		}
	}

	@Override
	public void openInventory() {
		if (this.numUsingPlayers < 0) {
			this.numUsingPlayers = 0;
		}

		++this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numUsingPlayers);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
	}

	@Override
	public void closeInventory() {
		--this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numUsingPlayers);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
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