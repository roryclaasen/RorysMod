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

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.roryclaasen.rorysmod.container.ContainerMachineRenamer;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.entity.tile.TileEntityRenamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMachineRenamer extends GuiContainer {

	private ResourceLocation texture = new ResourceLocation(RorysMod.MODID, "textures/gui/renamer.png");

	private InventoryPlayer inventory;
	private TileEntityRenamer tileEntity;

	private GuiTextField renameField;

	public GuiMachineRenamer(TileEntityRenamer tileEntity, EntityPlayer player) {
		super(new ContainerMachineRenamer(tileEntity, player));
		this.inventory = player.inventory;
		this.tileEntity = tileEntity;
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);

		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		renameField = new GuiTextField(this.fontRendererObj, i + 35, j + 21, 103, 12);
		renameField.setText(this.tileEntity.getCustomName());
		renameField.setEnableBackgroundDrawing(false);
		renameField.setMaxStringLength(40);
		renameField.setEnabled(true);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void keyTyped(char character, int code) {
		if (this.renameField.textboxKeyTyped(character, code)) {
			this.tileEntity.setRenameName(this.renameField.getText());
		} else {
			super.keyTyped(character, code);
		}
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.renameField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
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

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		this.renameField.drawTextBox();
	}
}