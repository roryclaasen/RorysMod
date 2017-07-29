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

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cofh.lib.gui.GuiBase;
import cpw.mods.fml.client.config.GuiSlider;
import me.roryclaasen.rorysmod.block.tile.TileEntityRifleTable;
import me.roryclaasen.rorysmod.container.ContainerRifleTable;
import me.roryclaasen.rorysmod.core.RorysGlobal;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.RorysConfig;
import me.roryclaasen.rorysmod.item.tools.ItemRifle;
import me.roryclaasen.rorysmod.util.ColorUtils;
import me.roryclaasen.rorysmod.util.NBTLaser;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiRifleTable extends GuiBase {

	private TileEntityRifleTable tileEntity;

	private static ResourceLocation empty = new ResourceLocation(RorysGlobal.MODID, "textures/gui/table-empty.png");
	private static ResourceLocation table = new ResourceLocation(RorysGlobal.MODID, "textures/gui/table.png");

	private GuiSlider colorR, colorG, colorB;
	private GuiButton save;

	public GuiRifleTable(InventoryPlayer inventoryPlayer, TileEntityRifleTable tileEntity) {
		super(new ContainerRifleTable(inventoryPlayer, tileEntity), empty);
		this.tileEntity = tileEntity;
		this.tileEntity.setGuiRifleTable(this);

		colorR = new GuiSlider(1, -100, 0, 100, 20, "Red: ", "", 0, 255, 255, true, true);
		colorG = new GuiSlider(2, -100, 30, 100, 20, "Green: ", "", 0, 255, 0, true, true);
		colorB = new GuiSlider(3, -100, 60, 100, 20, "Blue: ", "", 0, 255, 0, true, true);
		save = new GuiButton(4, -100, 90, 100, 20, "Save");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(colorR);
		buttonList.add(colorG);
		buttonList.add(colorB);
		buttonList.add(save);

		this.name = StatCollector.translateToLocal(RorysMod.GUIS.RILE_TABLE.getName() + ".title");
		this.drawInventory = false;
		this.drawTitle = false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		super.drawGuiContainerForegroundLayer(param1, param2);
		if (slidersEnabled()) fontRendererObj.drawString((StatCollector.translateToLocal(RorysMod.GUIS.RILE_TABLE.getName() + ".color")), -colorR.width + 10, 10, ColorUtils.getIntFromColor(getColorFromSlider()));
		if (tileEntity.hasLaser()) {
			NBTLaser nbt = new NBTLaser(tileEntity.getLaser());
			EnumChatFormatting color = EnumChatFormatting.DARK_GREEN;
			if (nbt.getWeight() > NBTLaser.getMaxWeight(((ItemRifle) tileEntity.getLaser().getItem()).getTier())) color = EnumChatFormatting.DARK_RED;
			fontRendererObj.drawString(StatCollector.translateToLocal("message.rorysmod.weight") + ": " + color + nbt.getWeight() + "/" + NBTLaser.getMaxWeight(((ItemRifle) tileEntity.getLaser().getItem()).getTier()), 8, ySize - 96 + 2, 4210752);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		if (RorysConfig.showColorBox && slidersEnabled()) {
			int id = ColorUtils.loadTextureFromColour(getColorFromSlider(), 64, 64);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			this.drawTexturedModalRect(colorB.xPosition + ((colorB.width - 32) / 2), colorB.yPosition + colorB.height + 10, 0, 0, 32, 32);
		}
	}

	@Override
	public void updateScreen() {
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		colorR.xPosition = x - (colorR.width + 10);
		colorG.xPosition = x - (colorG.width + 10);
		colorB.xPosition = x - (colorB.width + 10);
		save.xPosition = x - (save.width + 10);

		colorR.yPosition = y + ((ySize + (colorR.height * 3)) / 5) - 20;
		colorG.yPosition = colorR.yPosition + colorR.height + 10;
		colorB.yPosition = colorG.yPosition + colorG.height + 10;
		save.yPosition = colorB.yPosition + colorB.height + 10;

		setSlidersEnabled(tileEntity.hasLens());

		if (tileEntity.hasLaser()) this.texture = table;
		else this.texture = empty;
		super.updateScreen();
	}

	public void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 1:
				// tileEntity.setColor(getColorFromSlider());
				break;
			case 2:
				// tileEntity.setColor(getColorFromSlider());
				break;
			case 3:
				// tileEntity.setColor(getColorFromSlider());
				break;
			case 4:
				tileEntity.setColor(getColorFromSlider());
				break;
			default:
				break;
		}
	}

	public boolean slidersEnabled() {
		return colorR.enabled && colorG.enabled && colorB.enabled;
	}

	public void setSlidersEnabled(boolean enable) {
		colorR.enabled = enable;
		colorG.enabled = enable;
		colorB.enabled = enable;
		save.enabled = enable;

		colorR.visible = enable;
		colorG.visible = enable;
		colorB.visible = enable;
		save.visible = enable;
	}

	private Color getColorFromSlider() {
		return new Color(colorR.getValueInt(), colorG.getValueInt(), colorB.getValueInt());
	}

	public void setColorSlider(Color color) {
		colorR.setValue(color.getRed());
		colorG.setValue(color.getGreen());
		colorB.setValue(color.getBlue());
		colorR.displayString = colorR.dispString + colorR.getValueInt();
		colorG.displayString = colorG.dispString + colorG.getValueInt();
		colorB.displayString = colorB.dispString + colorB.getValueInt();
	}
}
