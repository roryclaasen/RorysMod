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
package me.roryclaasen.rorysmod.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import me.roryclaasen.rorysmod.block.tile.TileEntityPoweredChest;
import me.roryclaasen.rorysmod.core.RorysGlobal;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPoweredChest extends TileEntitySpecialRenderer {

	private ResourceLocation texture = new ResourceLocation(RorysGlobal.MODID + ":textures/models/chest.png");

	private ModelChest model;

	public RenderPoweredChest() {
		model = new ModelChest();
	}

	public void renderTileEntityAt(TileEntityPoweredChest tile, double x, double y, double z, float partialTick) {
		if (tile == null) return;
		int facing = 3;
		if (tile.hasWorldObj()) facing = tile.getFacing();

		this.bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		int k = 0;
		if (facing == 2) k = 180;
		if (facing == 3) k = 0;
		if (facing == 4) k = 90;
		if (facing == 5) k = -90;

		GL11.glRotatef(k, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		float lidangle = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partialTick;
		lidangle = 1.0F - lidangle;
		lidangle = 1.0F - lidangle * lidangle * lidangle;
		model.chestLid.rotateAngleX = (float) -((lidangle * Math.PI) / 2.0F);
		//RMLog.info(tile.lidAngle);
	//	model.chestLid.rotateAngleX = -lidangle;

		//tile.prevLidAngle = tile.lidAngle;
		
		model.renderAll();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick) {
		renderTileEntityAt((TileEntityPoweredChest) te, x, y, z, partialTick);
	}

}
