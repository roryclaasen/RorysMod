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
package me.roryclaasen.rorysmod.core.intergrations.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import me.roryclaasen.rorysmod.entity.tile.TileEntityRenamer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class WailaMachineRenamer extends BaseWailaDataProvider {
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileEntity tileEntity = accessor.getTileEntity();
		if (tileEntity instanceof TileEntityRenamer) {
			TileEntityRenamer renamer = (TileEntityRenamer) accessor.getTileEntity();
			
			// EnergyStorage energy = renamer.getEnergy();
			// currenttip.add(StatCollector.translateToLocal("message.rorysmod.energyStored") + " : " + energy.getEnergyStored() + "/" + energy.getMaxEnergyStored() + " RF");
			
			currenttip.add(StatCollector.translateToLocal("message.rorysmod.custName") + ": " + renamer.getCustomName());
		}
		return currenttip;
	}
}
