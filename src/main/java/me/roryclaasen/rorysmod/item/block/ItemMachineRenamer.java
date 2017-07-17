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
package me.roryclaasen.rorysmod.item.block;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import me.roryclaasen.rorysmod.entity.tile.TileEntityRenamer;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemMachineRenamer extends ItemBlock {

	public ItemMachineRenamer(Block block) {
		super(block);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (GuiScreen.isShiftKeyDown()) {
			NBTTagCompound data = itemstack.stackTagCompound;
			String renamingName = "";
			EnergyStorage energy = new EnergyStorage(TileEntityRenamer.ENERGY_STORAGE);
			energy.setEnergyStored(0);
			if (data != null && data.hasKey("blockData")) {
				NBTTagCompound tileEntity = data.getCompoundTag("blockData");
				energy.readFromNBT(tileEntity);
				renamingName = tileEntity.getString("settingName");
			}
			list.add(StatCollector.translateToLocal("message.rorysmod.energyStored") + " : " + energy.getEnergyStored() + "/" + energy.getMaxEnergyStored() + " RF");
			list.add(StatCollector.translateToLocal("message.rorysmod.custName") + " : " + renamingName);
		} else list.add(StatCollector.translateToLocal("message.rorysmod.holdShift1") + " " + EnumChatFormatting.YELLOW + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("message.rorysmod.holdShift2") + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + StatCollector.translateToLocal("message.rorysmod.holdShift3"));
	}
}
