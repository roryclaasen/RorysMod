package net.roryclaasen.rorysmod.entity.tile;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.core.ModItems;
import net.roryclaasen.rorysmod.gui.GuiRifleTable;
import net.roryclaasen.rorysmod.item.ItemRifle;
import net.roryclaasen.rorysmod.util.LaserData;

public class TileEntityRifleTable extends TileEntity implements IInventory {

	private ItemStack[] inv;

	private GuiRifleTable gui;

	public TileEntityRifleTable() {
		inv = new ItemStack[8];
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
				LaserData data = new LaserData(stack.stackTagCompound);
				{// Capacitor
					int capacitor = data.getCapacitor();
					if (capacitor > 0) {
						int extra = 0;
						if (capacitor > 16) {
							extra = capacitor - 16;
							capacitor = 16;
						}
						inv[1] = new ItemStack(ModItems.rifleUpgrade, capacitor, 1);
						if (extra > 0) inv[2] = new ItemStack(ModItems.rifleUpgrade, 1, extra);
					}
				}
				{// Coolant
					int coolant = data.getCoolant();
					if (coolant > 0) {
						inv[3] = new ItemStack(ModItems.rifleUpgrade, coolant, 2);
					}
				}
				{// Lens
					int lens = data.getLens();
					if (lens > 0) {
						inv[6] = new ItemStack(ModItems.rifleUpgrade, lens, 3);
					}
				}
				{// Phaser
					int phaser = data.getPhaser();
					if (phaser > 0) {
						inv[5] = new ItemStack(ModItems.rifleUpgrade, phaser, 4);
					}
				}
				{// Overclock
					int overclock = data.getOverclock();
					if (overclock > 0) {
						inv[7] = new ItemStack(ModItems.rifleUpgrade, overclock, 5);
					}
				}
				{// Explosion
					int explosion = data.getExplosion();
					if (explosion > 0) {
						inv[4] = new ItemStack(ModItems.rifleUpgrade, explosion, 6);
					}
				}// Color
				{
					if (gui != null) {
						gui.setColorSlider(data.getColor());
					}
				}
			} else if (hasLaser()) {
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
			LaserData data = new LaserData();
			int capacitor = (inv[1] != null) ? inv[1].stackSize : 0;
			if (inv[2] != null) capacitor += inv[2].stackSize;
			int coolant = (inv[3] != null) ? inv[3].stackSize : 0;
			int overclock = (inv[7] != null) ? inv[7].stackSize : 0;
			int lens = (inv[6] != null) ? inv[6].stackSize : 0;
			int phaser = (inv[5] != null) ? inv[5].stackSize : 0;
			int explosion = (inv[4] != null) ? inv[4].stackSize : 0;

			Color color = gui.getColorFromSlider();
			data.setData(capacitor, coolant, overclock, lens, phaser, explosion, color);
			inv[0].stackTagCompound = data.addToNBTTagCompound(inv[0].stackTagCompound);
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
		return (inv[6] != null) ? true : false;
	}

	public void setColor(Color colorFromSlider) {
		if (hasLens()) {
			writeToLaser();
		}
	}
}
