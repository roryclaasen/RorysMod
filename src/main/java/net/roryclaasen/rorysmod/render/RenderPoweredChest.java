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

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.entity.tile.TileEntityPoweredChest;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderPoweredChest extends TileEntitySpecialRenderer {

	private ResourceLocation texture = new ResourceLocation(RorysMod.MODID + ":textures/models/chest.png");

	private ModelChest model;

	public RenderPoweredChest() {
		model = new ModelChest();
	}

	public void renderTileEntityAt(TileEntityPoweredChest te, double x, double y, double z, float scale) {
		int i = 0;
		if (te.hasWorldObj()) {
			i = te.getBlockMetadata();
		}
		this.bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short short1 = 0;
		if (i == 2) {
			short1 = 180;
		}
		if (i == 3) {
			short1 = 0;
		}
		if (i == 4) {
			short1 = 90;
		}
		if (i == 5) {
			short1 = -90;
		}
		GL11.glRotatef((float) short1, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);float f1 = te.field_145975_i + (te.field_145972_a - te.field_145975_i) * scale;
		f1 = 1.0F - f1;
		f1 = 1.0F - f1 * f1 * f1;
		this.model.chestLid.rotateAngleX = -(f1 * (float) Math.PI / 2.0F);
		this.model.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		renderTileEntityAt((TileEntityPoweredChest) te, x, y, z, scale);
	}

}
