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
package me.roryclaasen.rorysmod.util;

import java.awt.Color;

import me.roryclaasen.rorysmod.core.RorysConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTLaser {

	public enum Items {
		Capacitor, Coolant, Lens, Phaser, Overclock, Explosion, Igniter
	}

	private NBTTagCompound tag;

	public static final int NO_SLOTS = 6;

	public NBTLaser(ItemStack stack) {
		this(stack.stackTagCompound);
	}

	public NBTLaser(NBTTagCompound currentTag) {
		if (currentTag == null) currentTag = new NBTTagCompound();
		this.tag = (NBTTagCompound) currentTag.copy();
		setUp();
	}

	public void setUp() {
		for (int i = 0; i < NO_SLOTS; i++) {
			if (!tag.hasKey("slotId_" + i)) tag.setInteger("slotId_" + i, -1);
			if (!tag.hasKey("slotQu_" + i)) tag.setInteger("slotQu_" + i, 0);
		}
		if (!tag.hasKey("lens")) tag.setBoolean("lens", false);
		if (!tag.hasKey("cooldown")) tag.setInteger("cooldown", 0);
		if (!tag.hasKey("cooldownMax")) tag.setInteger("cooldownMax", 100);
		if (!tag.hasKey("color")) {
			tag.setIntArray("color", ColorUtils.getIntArrayFromColor(Color.RED));
		}
	}

	public NBTTagCompound getTag() {
		return tag;
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
		return new int[] { getSlotId(slot), getSlotQuantity(slot) };
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

	public void setColor(int[] color) {
		tag.setIntArray("color", color);
	}

	public int[] getColor() {
		return tag.getIntArray("color");
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

	public Object getKey(String key) {
		return tag.getTag(key);
	}

	public static boolean hasKeys(NBTTagCompound ntbTag) {
		if (ntbTag == null) return false;
		if (ntbTag.hasNoTags()) return false;
		for (int i = 0; i < NO_SLOTS; i++) {
			if (!ntbTag.hasKey("slotId_" + i)) return false;
			if (!ntbTag.hasKey("slotQu_" + i)) return false;
		}
		if (!ntbTag.hasKey("lens")) return false;
		if (!ntbTag.hasKey("cooldown")) return false;
		if (!ntbTag.hasKey("cooldownMax")) return false;
		if (!ntbTag.hasKey("color")) return false;
		return true;
	}

	public static int getMaxWeight(int tier) {
		if (tier == 1) return RorysConfig.rifleTier1;
		if (tier == 2) return RorysConfig.rifleTier2;
		if (tier == 3) return RorysConfig.rifleTier3;
		if (tier == 4) return RorysConfig.rifleTier4;
		if (tier == 5) return RorysConfig.rifleTier5;
		// RMLog.warn("Unknown rifle tier");
		return RorysConfig.rifleTier1;
	}

	public String toString() {
		return this.getClass().getCanonicalName() + "->" + tag.toString();
	}
}
