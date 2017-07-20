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
package me.roryclaasen.rorysmod.item.base;

import java.util.List;

import cofh.api.energy.ItemEnergyContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.roryclaasen.rorysmod.core.RorysMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBaseEnergyContainer extends ItemEnergyContainer {

	private final String internalName;

	protected int baseCapacity;
	protected int tier = 1;

	public ItemBaseEnergyContainer(String unlocalizedName, int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
		this.setUnlocalizedName(RorysMod.MODID + "_" + unlocalizedName);
		this.setTextureName(RorysMod.MODID + ":" + unlocalizedName);
		this.setCreativeTab(RorysMod.tab);
		this.setMaxDamage(100);

		this.internalName = unlocalizedName;
		this.baseCapacity = capacity;
	}

	public ItemBaseEnergyContainer(String unlocalizedName, int capacity, int maxTransfer) {
		this(unlocalizedName, capacity, maxTransfer, maxTransfer);
	}

	public ItemBaseEnergyContainer(String unlocalizedName, int capacity) {
		this(unlocalizedName, capacity, capacity, capacity);
	}

	public ItemBaseEnergyContainer setTier(int tier) {
		this.tier = tier;
		return this;
	}

	public int getTier() {
		return this.tier;
	}

	public boolean use(ItemStack container, boolean simulate) {
		return extractEnergy(container, this.maxExtract, simulate) != 0;
	}

	public boolean use(ItemStack container, int energyAmount, boolean simulate) {
		return extractEnergy(container, energyAmount, simulate) != 0;
	}

	@Override
	public int receiveEnergy(ItemStack paramItemStack, int paramInt, boolean paramBoolean) {
		int receiveEnergy = super.receiveEnergy(paramItemStack, paramInt, paramBoolean);
		updateItemDamage(paramItemStack);
		return receiveEnergy;
	}

	@Override
	public int extractEnergy(ItemStack paramItemStack, int paramInt, boolean paramBoolean) {
		int extractEnergy = super.extractEnergy(paramItemStack, paramInt, paramBoolean);
		updateItemDamage(paramItemStack);
		return extractEnergy;
	}

	protected void updateItemDamage(ItemStack itemstack) {
		double energy = getEnergyStored(itemstack);
		double capacity = getMaxEnergyStored(itemstack);

		int percentage = (int) Math.round((energy / capacity) * 100.0D);
		itemstack.setItemDamage(itemstack.getMaxDamage() - percentage);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack container, EntityPlayer player, List tooltip, boolean advanced) {
		int energy = getEnergyStored(container);
		tooltip.add(energy + "/" + capacity + "RF");
	}

	public String getName() {
		return internalName;
	}
}
