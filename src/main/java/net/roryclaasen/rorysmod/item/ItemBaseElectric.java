package net.roryclaasen.rorysmod.item;

import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBaseElectric extends ItemBase implements IElectricItem {

	protected double maxCharge;
	protected double transferLimit;
	protected double usage;
	protected int tier;

	public ItemBaseElectric(String unlocalizedName, double maxCharge, double transferLimit, double usage, int tier) {
		super(unlocalizedName);
		this.maxCharge = maxCharge;
		this.transferLimit = transferLimit;
		this.usage = usage;
		this.tier = tier;
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return false;
	}

	@Override
	public Item getChargedItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return this.maxCharge;
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return this.tier;
	}

	@Override
	public double getTransferLimit(ItemStack itemStack) {
		return this.transferLimit;
	}

	public double getUsageCharge() {
		return this.usage;
	}
}
