package net.roryclaasen.rorysmod.render;

import java.awt.Color;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.core.Settings;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.model.ModelLaser;
import net.roryclaasen.rorysmod.util.ColorTexture;

import org.lwjgl.opengl.GL11;

public class RenderLaser extends Render {

	private static ResourceLocation backup = new ResourceLocation(RorysMod.MODID, "textures/entity/bolt.png");
	private ModelBase model;

	private int textureId = -1;

	public RenderLaser() {
		model = new ModelLaser();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return backup;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y - 1.25D, z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTick - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTick, 0.0F, 0.0F, 1.0F);
		bindTexture(backup);
		if (entity instanceof EntityLaser) {
			EntityLaser laser = (EntityLaser) entity;
			if (Settings.coloredLaser && laser.getLaserData() != null) {
				if (laser.getLaserData().getLens() > 0) GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTexture(laser.getLaserData().getColor()));
			}
		}

		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	public int getTexture(Color color) {
		if (textureId == -1) textureId = ColorTexture.loadTextureFromColour(color, 64, 32);
		return textureId;
	}
}
