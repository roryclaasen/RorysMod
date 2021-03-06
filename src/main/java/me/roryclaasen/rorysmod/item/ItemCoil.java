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
package me.roryclaasen.rorysmod.item;

import java.util.List;

import me.roryclaasen.rorysmod.item.base.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemCoil extends ItemBase {

	public ItemCoil(String unlocalizedName) {
		super(unlocalizedName);
		setMaxDamage(7);
		setMaxStackSize(1);
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemstack) {
		return false;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemstack) {
		ItemStack stack = itemstack.copy();
		stack.setItemDamage(stack.getItemDamage() + 1);
		return stack;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		tooltip.add(StatCollector.translateToLocal("message.rorymod.useslesft") + (stack.getMaxDamage() - stack.getItemDamage() + 1));
	}
}
