/*
 * Copyright 2017 Rory Claasen
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
package me.roryclaasen.rorysmod.item.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IBoxable;
import me.roryclaasen.rorysmod.item.base.ItemBaseEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSolderingIron extends ItemBaseEnergyContainer implements IBoxable {

	public ItemSolderingIron(String unlocalizedName) {
		super(unlocalizedName, 100, 2);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);

		this.canRepair = false;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		NBTTagCompound base = new NBTTagCompound();
		base.setInteger("Energy", 0);
		itemStack.writeToNBT(base);
		this.updateItemDamage(itemStack);
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack container, EntityPlayer playerIn, List tooltip, boolean advanced) {
		tooltip.add(StatCollector.translateToLocal("message.rorysmod.charge") + ": " + getEnergyStored(container) + " / " + getMaxEnergyStored(container) + " RF");
	}

	@Override
	public boolean canBeStoredInToolbox(ItemStack itemstack) {
		return true;
	}

	public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemstack) {
		return false;
	}

	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	public ItemStack getContainerItem(ItemStack itemstack) {
		ItemStack stack = itemstack.copy();
		((ItemBaseEnergyContainer) stack.getItem()).use(stack, 1, false);
		return stack;
	}
}
