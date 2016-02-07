package net.roryclaasen.rorysmod.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.model.ModelRifleTable;

import org.lwjgl.opengl.GL11;

public class TileEntityRenderRifleTable extends TileEntitySpecialRenderer {

	private ResourceLocation texture = new ResourceLocation(RorysMod.MODID + ":textures/models/table.png");

	private ModelRifleTable model;

	public TileEntityRenderRifleTable() {
		model = new ModelRifleTable();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.model.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
