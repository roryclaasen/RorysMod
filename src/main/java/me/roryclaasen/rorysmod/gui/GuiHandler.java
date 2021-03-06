/*
Copyright 2016-2017 Rory Claasen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.roryclaasen.rorysmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import me.roryclaasen.rorysmod.block.tile.TileEntityPoweredChest;
import me.roryclaasen.rorysmod.block.tile.TileEntityRenamer;
import me.roryclaasen.rorysmod.block.tile.TileEntityRifleTable;
import me.roryclaasen.rorysmod.container.ContainerMachineRenamer;
import me.roryclaasen.rorysmod.container.ContainerPoweredChest;
import me.roryclaasen.rorysmod.container.ContainerRifleTable;
import me.roryclaasen.rorysmod.core.RorysMod;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (ID == RorysMod.GUIS.RILE_TABLE.getId()) {
			if (tileEntity instanceof TileEntityRifleTable) {
				return new ContainerRifleTable(player.inventory, (TileEntityRifleTable) tileEntity);
			}
		}
		if (ID == RorysMod.GUIS.CHEST_POWERED.getId()) {
			if (tileEntity instanceof TileEntityPoweredChest) {
				return new ContainerPoweredChest((TileEntityPoweredChest) tileEntity, player);
			}
		}
		if (ID == RorysMod.GUIS.MACHINE_RENAMER.getId()) {
			if (tileEntity instanceof TileEntityRenamer) {
				return new ContainerMachineRenamer((TileEntityRenamer) tileEntity, player);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (ID == RorysMod.GUIS.RILE_TABLE.getId()) {
			if (tileEntity instanceof TileEntityRifleTable) {
				return new GuiRifleTable(player.inventory, (TileEntityRifleTable) tileEntity);
			}
		}
		if (ID == RorysMod.GUIS.CHEST_POWERED.getId()) {
			if (tileEntity instanceof TileEntityPoweredChest) {
				return new GuiPoweredChest((TileEntityPoweredChest) tileEntity, player);
			}
		}
		if (ID == RorysMod.GUIS.MACHINE_RENAMER.getId()) {
			if (tileEntity instanceof TileEntityRenamer) {
				return new GuiMachineRenamer((TileEntityRenamer) tileEntity, player);
			}
		}
		return null;
	}
}
