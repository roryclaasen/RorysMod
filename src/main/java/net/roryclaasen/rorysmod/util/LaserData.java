package net.roryclaasen.rorysmod.util;

import java.awt.Color;

import net.minecraft.nbt.NBTTagCompound;

public class LaserData {

	private NBTTagCompound stackTagCompound;

	public LaserData(NBTTagCompound stackTagCompound) {
		this.stackTagCompound = stackTagCompound;
	}

	public LaserData() {
		this(new NBTTagCompound());
		this.reset();
	}

	public void reset() {
		this.setData(1, 0, 0, 0, 0, 0, 0, null);
	}

	public int getInteger(String key) {
		if (this.stackTagCompound.hasKey(key)) return this.stackTagCompound.getInteger(key);
		else {
			RMLog.fatal("There is no key called " + key);
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
		this.stackTagCompound.setInteger(key, value);
	}

	public void setColor(Color color) {
		if (color == null) {
			color = Color.RED;
		}
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
		this.setData(this.getTier(), capacitor, coolant, overclock, lens, phaser, explosion, color);
	}

	public void setData(int capacitor, int coolant, int overclock, int lens, int phaser, int explosion) {
		this.setData(this.getTier(), capacitor, coolant, overclock, lens, phaser, explosion, null);
	}

	public void setNBT(NBTTagCompound stackTagCompound) {
		this.stackTagCompound = stackTagCompound;
	}

	public NBTTagCompound getNBT() {
		return stackTagCompound;
	}
}
