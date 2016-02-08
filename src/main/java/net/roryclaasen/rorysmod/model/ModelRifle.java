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
package net.roryclaasen.rorysmod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRifle extends ModelBase {

	// fields
	ModelRenderer gunMainBody;
	ModelRenderer gunBodySegment;
	ModelRenderer gunPad;
	ModelRenderer armSupport1;
	ModelRenderer armSupport2;
	ModelRenderer armSupport3;
	ModelRenderer armSupport4;
	ModelRenderer barrel;
	ModelRenderer prong;
	ModelRenderer asthethic1;
	ModelRenderer aesthetic2;
	ModelRenderer toothThing;
	ModelRenderer Shape1;

	public ModelRifle() {
		textureWidth = 64;
		textureHeight = 32;

		gunMainBody = new ModelRenderer(this, 42, 20);
		gunMainBody.addBox(-1F, 0F, 0F, 2, 3, 9);
		gunMainBody.setRotationPoint(6F, -3.5F, -14F);
		gunMainBody.setTextureSize(64, 32);
		gunMainBody.mirror = true;
		setRotation(gunMainBody, 0F, 0F, 0F);
		gunBodySegment = new ModelRenderer(this, 48, 11);
		gunBodySegment.addBox(-1F, 0F, -0.2F, 2, 2, 6);
		gunBodySegment.setRotationPoint(6F, -2F, -6F);
		gunBodySegment.setTextureSize(64, 32);
		gunBodySegment.mirror = true;
		setRotation(gunBodySegment, 0.0872665F, 0F, 0F);
		gunPad = new ModelRenderer(this, 52, 3);
		gunPad.addBox(-1F, 0F, 0F, 2, 3, 4);
		gunPad.setRotationPoint(6F, -2.8F, -0.3F);
		gunPad.setTextureSize(64, 32);
		gunPad.mirror = true;
		setRotation(gunPad, -0.0523599F, 0F, 0F);
		armSupport1 = new ModelRenderer(this, 35, 27);
		armSupport1.addBox(-0.5F, -0.2F, 0F, 1, 3, 2);
		armSupport1.setRotationPoint(6F, -0.5F, -1F);
		armSupport1.setTextureSize(64, 32);
		armSupport1.mirror = true;
		setRotation(armSupport1, -0.4537856F, 0F, 0F);
		armSupport2 = new ModelRenderer(this, 20, 24);
		armSupport2.addBox(-0.5F, -1F, -6F, 1, 1, 6);
		armSupport2.setRotationPoint(6F, 3F, -1F);
		armSupport2.setTextureSize(64, 32);
		armSupport2.mirror = true;
		setRotation(armSupport2, -0.418879F, 0F, 0F);
		armSupport3 = new ModelRenderer(this, 20, 24);
		armSupport3.addBox(-0.5F, -2F, -5F, 1, 2, 6);
		armSupport3.setRotationPoint(6F, 0.6F, -6.3F);
		armSupport3.setTextureSize(64, 32);
		armSupport3.mirror = true;
		setRotation(armSupport3, -0.0523599F, 0F, 0F);
		armSupport4 = new ModelRenderer(this, 23, 26);
		armSupport4.addBox(-0.5F, 0F, 0F, 1, 1, 3);
		armSupport4.setRotationPoint(6F, -0.7F, -14F);
		armSupport4.setTextureSize(64, 32);
		armSupport4.mirror = true;
		setRotation(armSupport4, 0F, 0F, 0F);
		barrel = new ModelRenderer(this, 0, 0);
		barrel.addBox(-0.5F, -0.5F, -1F, 1, 1, 1);
		barrel.setRotationPoint(6F, -2.5F, -14F);
		barrel.setTextureSize(64, 32);
		barrel.mirror = true;
		setRotation(barrel, 0F, 0F, 0F);
		prong = new ModelRenderer(this, 0, 4);
		prong.addBox(-0.5F, -1F, -4F, 1, 2, 4);
		prong.setRotationPoint(7F, -2F, -11.5F);
		prong.setTextureSize(64, 32);
		prong.mirror = true;
		setRotation(prong, 0F, 0F, 0F);
		asthethic1 = new ModelRenderer(this, 21, 0);
		asthethic1.addBox(-0.5F, -1.5F, 0F, 1, 2, 1);
		asthethic1.setRotationPoint(6F, -2F, -5.5F);
		asthethic1.setTextureSize(64, 32);
		asthethic1.mirror = true;
		setRotation(asthethic1, -0.7086656F, 0F, 0F);
		aesthetic2 = new ModelRenderer(this, 26, 0);
		aesthetic2.addBox(-1.5F, 0F, 0F, 3, 2, 3);
		aesthetic2.setRotationPoint(6F, -2F, -10F);
		aesthetic2.setTextureSize(64, 32);
		aesthetic2.mirror = true;
		setRotation(aesthetic2, 0F, 0F, 0F);
		toothThing = new ModelRenderer(this, 0, 6);
		toothThing.addBox(0F, 0F, 0F, 0, 1, 5);
		toothThing.setRotationPoint(6F, -4F, -13F);
		toothThing.setTextureSize(64, 32);
		toothThing.mirror = true;
		setRotation(toothThing, 0F, 0F, 0F);
		Shape1 = new ModelRenderer(this, 0, 13);
		Shape1.addBox(-0.5F, -0.8F, 0F, 1, 4, 1);
		Shape1.setRotationPoint(6F, 0F, -12.5F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0.2617994F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		gunMainBody.render(f5);
		gunBodySegment.render(f5);
		gunPad.render(f5);
		armSupport1.render(f5);
		armSupport2.render(f5);
		armSupport3.render(f5);
		armSupport4.render(f5);
		barrel.render(f5);
		prong.render(f5);
		asthethic1.render(f5);
		aesthetic2.render(f5);
		toothThing.render(f5);
		Shape1.render(f5);
	}

	public void render(float f5) {
		gunMainBody.render(f5);
		gunBodySegment.render(f5);
		gunPad.render(f5);
		armSupport1.render(f5);
		armSupport2.render(f5);
		armSupport3.render(f5);
		armSupport4.render(f5);
		barrel.render(f5);
		prong.render(f5);
		asthethic1.render(f5);
		aesthetic2.render(f5);
		toothThing.render(f5);
		Shape1.render(f5);
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