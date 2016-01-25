package net.roryclaasen.rorysmod.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.model.ModelLaserBolt;

import org.lwjgl.opengl.GL11;

public class RenderLaserBolt extends Render {

	private static final ResourceLocation texture = new ResourceLocation(RorysMod.MODID, "textures/entity/bolt.png");
	private ModelBase model;

	public RenderLaserBolt() {
		model = new ModelLaserBolt();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		bindTexture(texture);
		GL11.glTranslated(x, y - 1.25D, z);
		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}