package net.roryclaasen.rorysmod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRifleTable extends ModelBase {

	// fields
	ModelRenderer base;
	ModelRenderer supportBottom;
	ModelRenderer pillar;
	ModelRenderer supportTop;
	ModelRenderer pad;

	public ModelRifleTable() {
		textureWidth = 128;
		textureHeight = 64;

		base = new ModelRenderer(this, 0, 47);
		base.addBox(0F, 0F, 0F, 16, 1, 16);
		base.setRotationPoint(-8F, 23F, -8F);
		base.setTextureSize(128, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		supportBottom = new ModelRenderer(this, 0, 34);
		supportBottom.addBox(0F, 0F, 0F, 12, 1, 12);
		supportBottom.setRotationPoint(-6F, 22F, -6F);
		supportBottom.setTextureSize(128, 64);
		supportBottom.mirror = true;
		setRotation(supportBottom, 0F, 0F, 0F);
		pillar = new ModelRenderer(this, 0, 0);
		pillar.addBox(0F, 0F, 0F, 6, 7, 6);
		pillar.setRotationPoint(-3F, 15F, -3F);
		pillar.setTextureSize(128, 64);
		pillar.mirror = true;
		setRotation(pillar, 0F, 0F, 0F);
		supportTop = new ModelRenderer(this, 0, 21);
		supportTop.addBox(0F, 0F, 0F, 12, 1, 12);
		supportTop.setRotationPoint(-6F, 14F, -6F);
		supportTop.setTextureSize(128, 64);
		supportTop.mirror = true;
		setRotation(supportTop, 0F, 0F, 0F);
		pad = new ModelRenderer(this, 48, 21);
		pad.addBox(0F, 0F, 0F, 16, 4, 16);
		pad.setRotationPoint(-8F, 10F, -8F);
		pad.setTextureSize(128, 64);
		pad.mirror = true;
		setRotation(pad, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base.render(f5);
		supportBottom.render(f5);
		pillar.render(f5);
		supportTop.render(f5);
		pad.render(f5);
	}

	public void render(float f5) {
		base.render(f5);
		supportBottom.render(f5);
		pillar.render(f5);
		supportTop.render(f5);
		pad.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
