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
import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementTextField;
import me.roryclaasen.rorysmod.block.tile.TileEntityRenamer;
import me.roryclaasen.rorysmod.container.ContainerMachineRenamer;
import me.roryclaasen.rorysmod.core.RorysMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiMachineRenamer extends GuiBase {

	private static final ResourceLocation TEXTURE = new ResourceLocation(RorysMod.MODID, "textures/gui/renamer.png");

	private TileEntityRenamer tileEntity;

	private ElementTextField renameField;
	private ElementDualScaled progress;

	public GuiMachineRenamer(TileEntityRenamer tileEntity, EntityPlayer player) {
		super(new ContainerMachineRenamer(tileEntity, player), TEXTURE);
		this.tileEntity = tileEntity;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		this.tileEntity.sync();
		
		elements.add(getEnergyStoredElement());

		renameField = new ElementTextField(this, 43, 20, 103, 12);
		renameField.setText(this.tileEntity.getCustomName());
		renameField.setMaxLength((short) 40);
		renameField.backgroundColor = 0;
		renameField.borderColor = 0;
		elements.add(renameField);

		this.progress = ((ElementDualScaled) addElement(new ElementDualScaled(this, 86, 46).setMode(1).setSize(24, 16).setTexture("cofh:textures/gui/elements/Progress_Arrow_Right.png", 64, 16)));
		this.name = StatCollector.translateToLocal(tileEntity.getInventoryName());
	}

	@Override
	protected void keyTyped(char characterTyped, int keyPressed) {
		for (int i = elements.size(); i-- > 0;) {
			ElementBase c = elements.get(i);
			if (!c.isVisible() || !c.isEnabled()) {
				continue;
			}
			if (c.onKeyTyped(characterTyped, keyPressed)) {
				if (c instanceof ElementTextField) {
					tileEntity.setRenameName(((ElementTextField) c).getText());
				}
				return;
			}
		}
		super.keyTyped(characterTyped, keyPressed);
	}

	@Override
	protected void mouseClicked(int paramInt1, int paramInt2, int paramInt3) {
		super.mouseClicked(paramInt1, paramInt2, paramInt3);

		this.tileEntity.setActive(!renameField.isFocused());
		this.tileEntity.sendGUIUpdate();
	}

	@Override
	protected void updateElementInformation() {
		super.updateElementInformation();
		this.progress.setQuantity(this.tileEntity.getScaledProgress(24));
		this.elements.set(0, getEnergyStoredElement());
	}
	
	private ElementEnergyStored getEnergyStoredElement() {
		return new ElementEnergyStored(this, 8, 17, this.tileEntity.getEnergy());
	}
}