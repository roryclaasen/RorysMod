/*
Copyright 2016 Rory Claasen

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
package net.roryclaasen.rorysmod.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.model.ModelRifle;

import org.lwjgl.opengl.GL11;

public class RenderRifle implements IItemRenderer {

	protected ModelRifle rifle;

	public RenderRifle() {
		rifle = new ModelRifle();
	}

	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
			case EQUIPPED :
				return true;
			case EQUIPPED_FIRST_PERSON :
				return true;
			case ENTITY :
				return false;
			case INVENTORY :
				return false;
			default :
				return false;
		}
	}

	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		boolean isFirstPerson = false;
		if (data.length > 1) {
			if (data[1] != null && data[1] instanceof EntityPlayer) {
				if (((EntityPlayer) data[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F))) {
					isFirstPerson = true;
				}
			}
		}
		switch (type) {
			case EQUIPPED : {
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RorysMod.MODID, "textures/models/laserRifle.png"));

				GL11.glRotatef(80F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(25F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-80F, 0.0F, 0.0F, 1.0F);

				GL11.glRotatef(85F, 1F, 0.0F, 0.0F);
				GL11.glRotatef(-10F, 0, 0.0F, 1F);
				GL11.glTranslatef(-0.45F, 0.0F, 0.15F);
				float scale = 1.4F;
				GL11.glScaled(scale, scale, scale);
				rifle.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				GL11.glPopMatrix();
				break;
			}
			case EQUIPPED_FIRST_PERSON : {
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RorysMod.MODID, "textures/models/laserRifle.png"));

				if (isFirstPerson) {
					GL11.glRotatef(80F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(25F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-80F, 0.0F, 0.0F, 1.0F);

					GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(-1F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(5.5F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-0.50F, 0.2F, -0.5F);

					float scale = 1.4F;
					GL11.glScaled(scale, scale, scale);
					rifle.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
					GL11.glPopMatrix();
					break;
				}
			}
			default : {
				break;
			}
		}
	}
}
