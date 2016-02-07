package net.roryclaasen.rorysmod.util;

import java.awt.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.roryclaasen.rorysmod.core.Settings;

public class NBTLaser {

	public enum Items {
		Capacitor, Coolant, Lens, Phaser, Overclock, Explosion
	}

	private NBTTagCompound tag;

	public static final int NO_SLOTS = 6;

	public NBTLaser(ItemStack stack) {
		this(stack.stackTagCompound);
	}

	public NBTLaser(NBTTagCompound tag) {
		if (tag == null) {
			this.tag = new NBTTagCompound();
			setUp();
		}
		this.tag = tag;
	}

	public void setUp() {
		for (int i = 0; i < NO_SLOTS; i++) {
			tag.setInteger("slotId_" + i, -1);
			tag.setInteger("slotQu_" + i, 0);
		}
		tag.setBoolean("lens", false);
		setColor(null);
		tag.setInteger("cooldown", 0);
		tag.setInteger("cooldownMax", 100);
	}

	public NBTTagCompound getTag() {
		return tag;
	}

	public Color getColor() {
		int color = tag.getInteger("color");
		return new Color(color);
	}

	public void setColor(Color color) {
		if (color == null) color = Color.RED;
		setColor(ColorUtils.getIntFromColor(color));
	}

	public void setColor(int color) {
		tag.setInteger("color", color);
	}

	public void setLens(boolean hasLens) {
		tag.setBoolean("lens", hasLens);
	}

	public void setSlot(int slot, int item, int quanity) {
		tag.setInteger("slotId_" + slot, item);
		tag.setInteger("slotQu_" + slot, quanity);
	}

	public boolean hasLens() {
		return tag.getBoolean("lens");
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
		if (slot < 0 || slot >= NO_SLOTS) return -1;
		return tag.getInteger("slotId_" + slot);
	}

	private int getSlotQuantity(int slot) {
		if (slot < 0 || slot >= NO_SLOTS) return 0;
		return tag.getInteger("slotQu_" + slot);
	}

	public int getItemCount(Items item) {
		int count = 0;
		for (int[] slot : getSlots()) {
			if (slot[0] == item.ordinal()) count += slot[1];
		}
		return count;
	}

	public int getItemCount(int item) {
		int count = 0;
		for (int[] slot : getSlots()) {
			if (slot[0] == item) count += slot[1];
		}
		return count;
	}

	public int getCooldown() {
		return tag.getInteger("cooldown");
	}

	public int getMaxCooldown() {
		return tag.getInteger("cooldownMax");
	}

	public void setCooldown(int cooldown) {
		tag.setInteger("cooldown", cooldown);
	}

	public void setMaxCooldown(int maxCooldown) {
		tag.setInteger("cooldownMax", maxCooldown);
	}

	public int getWeight() {
		int weight = 0;
		for (int i = 0; i < NO_SLOTS; i++) {
			weight += getSlotQuantity(i);
		}
		if (hasLens()) weight += 1;
		return weight;
	}

	public boolean overheating() {
		return getCooldown() > 0;
	}

	public boolean checkWeight(int tier) {
		return getWeight() <= getMaxWeight(tier);
	}

	public static boolean hasKeys(NBTTagCompound ntbTag) {
		// TODO Fix function
		if (ntbTag == null) return false;
		if (ntbTag.hasNoTags()) return false;
		for (int i = 0; i < NO_SLOTS; i++) {
			if (!ntbTag.hasKey("slotId_" + i)) return false;
			if (!ntbTag.hasKey("slotQu_" + i)) return false;
		}
		if (!ntbTag.hasKey("color_r")) return false;
		if (!ntbTag.hasKey("color_g")) return false;
		if (!ntbTag.hasKey("color_b")) return false;
		if (!ntbTag.hasKey("lens")) return false;
		if (!ntbTag.hasKey("cooldown")) return false;
		if (!ntbTag.hasKey("cooldownMax")) return false;
		return true;
	}

	public static int getMaxWeight(int tier) {
		if (tier == 1) return Settings.rifleTier1;
		if (tier == 2) return Settings.rifleTier2;
		if (tier == 3) return Settings.rifleTier3;
		if (tier == 4) return Settings.rifleTier4;
		if (tier == 5) return Settings.rifleTier5;
		// RMLog.warn("Unknown rifle tier");
		return Settings.rifleTier1;
	}
}
