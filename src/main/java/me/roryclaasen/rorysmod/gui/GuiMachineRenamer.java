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

import org.lwjgl.opengl.GL11;

import me.roryclaasen.rorysmod.container.ContainerMachineRenamer;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.entity.tile.TileEntityRenamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMachineRenamer extends GuiContainer {

	private ResourceLocation texture = new ResourceLocation(RorysMod.MODID, "textures/gui/renamer.png");

	private InventoryPlayer inventory;
	private TileEntityRenamer tileEntity;

	public GuiMachineRenamer(TileEntityRenamer tileEntity, EntityPlayer player) {
		super(new ContainerMachineRenamer(tileEntity, player));
		this.inventory = player.inventory;
		this.tileEntity = tileEntity;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(I18n.format(tileEntity.getInventoryName()), 8, 6, 4210752, false);
		fontRendererObj.drawString(I18n.format(inventory.getInventoryName()), 8, ySize - 96 + 2, 4210752);
	}
}