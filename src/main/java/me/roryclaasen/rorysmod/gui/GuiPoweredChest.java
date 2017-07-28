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
package me.roryclaasen.rorysmod.gui;

import cofh.lib.gui.GuiBase;
import me.roryclaasen.rorysmod.block.tile.TileEntityPoweredChest;
import me.roryclaasen.rorysmod.container.ContainerPoweredChest;
import me.roryclaasen.rorysmod.core.RorysGlobal;
import me.roryclaasen.rorysmod.core.RorysMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiPoweredChest extends GuiBase {

	public GuiPoweredChest(TileEntityPoweredChest te, EntityPlayer player) {
		super(new ContainerPoweredChest(te, player), new ResourceLocation(RorysGlobal.MODID, "textures/gui/chest.png"));
	}

	@Override
	public void initGui() {
		super.initGui();
		this.name = StatCollector.translateToLocal(RorysMod.GUIS.CHEST_POWERED.getName() + ".title");
	}
}