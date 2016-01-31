package net.roryclaasen.rorysmod.util;

import java.awt.Color;

import net.minecraft.nbt.NBTTagCompound;
import net.roryclaasen.rorysmod.core.Settings;

public class LaserData {

	private NBTTagCompound stackTagCompound;

	public static final Color DEFULT_COLOR = Color.RED;

	public LaserData(NBTTagCompound stackTagCompound) {
		if (stackTagCompound == null) {
			this.stackTagCompound = new NBTTagCompound();
			reset();
		} else this.stackTagCompound = stackTagCompound;
	}

	public LaserData() {
		this(new NBTTagCompound());
		this.reset();
	}

	public void reset() {
		this.setData(1, 0, 0, 0, 0, 0, 0, null);
	}

	public int getInteger(String key) {
		if (stackTagCompound.hasKey(key)) return stackTagCompound.getInteger(key);
		else {
			RMLog.fatal("There is no key called " + key);
			setInteger(key, 0);
			return 0;
		}
	}

	public int getTier() {
		return getInteger("tier");
	}

	public int getCapacitor() {
		return getInteger("capacitor");
	}

	public int getCoolant() {
		return getInteger("coolant");
	}

	public int getOverclock() {
		return getInteger("overclock");
	}

	public int getLens() {
		return getInteger("lens");
	}

	public int getPhaser() {
		return getInteger("phaser");
	}

	public int getExplosion() {
		return getInteger("explosion");
	}

	public Color getColor() {
		return new Color(getInteger("color_r"), getInteger("color_g"), getInteger("color_b"));
	}

	public void setInteger(String key, int value) {
		stackTagCompound.setInteger(key, value);
	}

	public void setColor(Color color) {
		if (color == null) color = DEFULT_COLOR;

		setInteger("color_r", color.getRed());
		setInteger("color_g", color.getGreen());
		setInteger("color_b", color.getBlue());
	}

	public void setData(int tier, int capacitor, int coolant, int overclock, int lens, int phaser, int explosion, Color color) {
		setInteger("tier", tier);
		setInteger("capacitor", capacitor);
		setInteger("coolant", coolant);
		setInteger("overclock", overclock);
		setInteger("lens", lens);
		setInteger("phaser", phaser);
		setInteger("explosion", explosion);

		setColor(color);
	}

	public void setData(int capacitor, int coolant, int overclock, int lens, int phaser, int explosion, Color color) {
		setData(getTier(), capacitor, coolant, overclock, lens, phaser, explosion, color);
	}

	public void setData(int capacitor, int coolant, int overclock, int lens, int phaser, int explosion) {
		setData(getTier(), capacitor, coolant, overclock, lens, phaser, explosion, null);
	}

	public void setNBT(NBTTagCompound stackTagCompound) {
		this.stackTagCompound = stackTagCompound;
	}

	public NBTTagCompound getNBT() {
		return stackTagCompound;
	}

	public int getWeight() {
		return getCapacitor() + getCoolant() + getOverclock() + getLens() + getPhaser() + getExplosion();
	}

	public boolean canFire() {
		int weight = getWeight();
		int maxWeight = getMaxWeight();
		return weight <= maxWeight;
	}
	
	public int getMaxWeight(){
		switch (getTier()) {
			case 1 :
				return Settings.rifleTier1;
			case 2 :
				return Settings.rifleTier2;
			case 3 :
				return Settings.rifleTier3;
			case 4 :
				return Settings.rifleTier4;
			case 5 :
				return Settings.rifleTier5;
		}
		return Settings.rifleTier1;
	}

	public static boolean hasAllKeys(NBTTagCompound stackTag) {
		if (stackTag == null) return false;
		if (!stackTag.hasKey("tier")) return false;
		if (!stackTag.hasKey("capacitor")) return false;
		if (!stackTag.hasKey("coolant")) return false;
		if (!stackTag.hasKey("overclock")) return false;
		if (!stackTag.hasKey("lens")) return false;
		if (!stackTag.hasKey("phaser")) return false;
		if (!stackTag.hasKey("explosion")) return false;
		return true;
	}

	public static NBTTagCompound getNewStackTagCompound() {
		NBTTagCompound stackTag = new NBTTagCompound();

		stackTag.setInteger("tier", 1);
		stackTag.setInteger("capacitor", 0);
		stackTag.setInteger("coolant", 0);
		stackTag.setInteger("overclock", 0);
		stackTag.setInteger("lens", 0);
		stackTag.setInteger("phaser", 0);
		stackTag.setInteger("explosion", 0);

		stackTag.setInteger("color_r", DEFULT_COLOR.getRed());
		stackTag.setInteger("color_g", DEFULT_COLOR.getGreen());
		stackTag.setInteger("color_b", DEFULT_COLOR.getBlue());
		return stackTag;
	}

	public NBTTagCompound addToNBTTagCompound(NBTTagCompound stackTag) {
		stackTag.setInteger("tier", getTier());
		stackTag.setInteger("capacitor", getCapacitor());
		stackTag.setInteger("coolant", getCoolant());
		stackTag.setInteger("overclock", getOverclock());
		stackTag.setInteger("lens", getLens());
		stackTag.setInteger("phaser", getPhaser());
		stackTag.setInteger("explosion", getExplosion());

		stackTag.setInteger("color_r", getColor().getRed());
		stackTag.setInteger("color_g", getColor().getGreen());
		stackTag.setInteger("color_b", getColor().getBlue());
		return stackTag;
	}
}
