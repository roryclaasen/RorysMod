package net.roryclaasen.rorysmod.util;

import java.awt.Color;

import net.minecraft.nbt.NBTTagCompound;

public class NBTLaser {

	private NBTTagCompound tag;

	public static final int NO_SLOTS = 8;

	public NBTLaser(NBTTagCompound tag) {
		this.tag = tag;
	}

	public void setUp() {
		for (int i = 0; i < NO_SLOTS; i++) {
			tag.setInteger("slotId_" + i, 0);
			tag.setInteger("slotQu_" + i, 0);
		}
		tag.setInteger("color_r", 255);
		tag.setInteger("color_g", 0);
		tag.setInteger("color_b", 0);
	}

	public NBTTagCompound getTag() {
		return tag;
	}

	public Color getColor() {
		int r = tag.getInteger("color_r");
		int g = tag.getInteger("color_g");
		int b = tag.getInteger("color_b");
		return new Color(r, g, b);
	}

	public void setColor(Color color) {
		setColor(color.getRed(), color.getGreen(), color.getBlue());
	}

	public void setColor(int red, int green, int blue) {
		tag.setInteger("color_r", red);
		tag.setInteger("color_g", green);
		tag.setInteger("color_b", blue);
	}

	public void setSlot(int slot, int item, int quanity) {
		tag.setInteger("slotId_" + slot, item);
		tag.setInteger("slotQu_" + slot, quanity);
	}

	public int[][] getSlots() {
		int[][] slots = new int[NO_SLOTS][2];
		for (int i = 0; i < NO_SLOTS; i++) {
			slots[i] = getSlot(i);
		}
		return slots;
	}

	public int[] getSlot(int slot) {
		if (slot < 0 || slot >= NO_SLOTS) return null;
		return new int[]{getSlotId(slot), getSlotQuantity(slot)};
	}

	private int getSlotId(int slot) {
		if (slot < 0 || slot >= NO_SLOTS) return 0;
		return tag.getInteger("slotId_" + slot);
	}

	private int getSlotQuantity(int slot) {
		if (slot < 0 || slot >= NO_SLOTS) return 0;
		return tag.getInteger("slotQu_" + slot);
	}

	public int getItemCount(int item) {
		int count = 0;
		for (int[] slot : getSlots()) {
			if (slot[0] == item) count += slot[1];
		}
		return count;
	}
}
