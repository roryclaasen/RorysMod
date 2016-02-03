package net.roryclaasen.rorysmod.entity.tile;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.container.ContainerRifleTable;
import net.roryclaasen.rorysmod.core.ModItems;
import net.roryclaasen.rorysmod.gui.GuiRifleTable;
import net.roryclaasen.rorysmod.item.ItemRifle;
import net.roryclaasen.rorysmod.item.ItemRifleUpgrade;
import net.roryclaasen.rorysmod.util.NBTLaser;

public class TileEntityRifleTable extends TileEntity implements IInventory {

	private ItemStack[] inv;

	private GuiRifleTable gui;

	public TileEntityRifleTable() {
		inv = new ItemStack[ContainerRifleTable.NO_CUSTOM_SLOTS];
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
				if (data.hasLens()) inv[1] = new ItemStack(ModItems.rifleUpgrade, 1, 3);
				for (int i = 3; i < ContainerRifleTable.NO_CUSTOM_SLOTS; i++) {
					int[] cont = data.getSlot(i - 3);
					if (cont[0] != -1) inv[i] = new ItemStack(ModItems.rifleUpgrade, cont[1], cont[0]);
				}
			}
			if (hasLaser()) {
				writeToLaser();
			}
		}
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
			for (int i = 3; i < ContainerRifleTable.NO_CUSTOM_SLOTS; i++) {
				ItemStack stack = inv[i];
				if (stack != null) {
					if (stack.getItem() instanceof ItemRifleUpgrade) {
						data.setSlot(3 - i, stack.getItemDamage(), stack.stackSize);
					}
				} else data.setSlot(3 - i, -1, 0);
			}
			inv[0].stackTagCompound = data.getTag();
			((ItemRifle) inv[0].getItem()).updateNBT(inv[0]);
		}
	}

	public boolean hasLaser() {
		if (inv[0] == null) return false;
		if (inv[0].getItem() == null) return false;
		return inv[0].getItem() instanceof ItemRifle;
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
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory", 0);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length) {
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}

	public boolean hasLens() {
		if (!hasLaser()) return false;
		NBTLaser data = new NBTLaser(inv[0].stackTagCompound);
		return data.hasLens();
	}

	public void setColor(Color colorFromSlider) {
		if (hasLens()) {
			writeToLaser();
		}
	}
}
