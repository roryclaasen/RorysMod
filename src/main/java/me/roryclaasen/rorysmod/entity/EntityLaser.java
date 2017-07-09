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
package me.roryclaasen.rorysmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import me.roryclaasen.rorysmod.core.Settings;
import me.roryclaasen.rorysmod.util.NBTLaser;

public class EntityLaser extends EntityThrowable {

	private NBTLaser data;
	private NBTTagCompound stackTag;

	public EntityLaser(World world) {
		super(world);
	}

	public EntityLaser(World world, EntityLivingBase entity, ItemStack itemStack) {
		super(world, entity);
		this.data = new NBTLaser(itemStack.stackTagCompound);
		this.stackTag = this.data.getTag();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (Settings.laserEmitsLight) addLight();
		if (ticksExisted > 60) {
			setDead();
		}
	}

	@Override
	public void setDead() {
		if (Settings.laserEmitsLight) this.worldObj.updateLightByType(EnumSkyBlock.Block, (int) this.posX, (int) this.posY, (int) this.posZ);
		super.setDead();
	}

	@Override
	protected float getGravityVelocity() {
		return 0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition movingObjectPosition) {
		if (!worldObj.isRemote) {
			if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				if (Settings.setFireToBlocks) {
					if (data.getItemCount(NBTLaser.Items.Igniter) > 0) {
						int x = movingObjectPosition.blockX, y = movingObjectPosition.blockY, z = movingObjectPosition.blockZ;
						boolean fire = true;
						switch (movingObjectPosition.sideHit) {
							case -1 : {
								fire = false;
							}
							case 0 : {
								--y;
								fire = false;
							}
							case 1 : {
								++y;
							}
							case 2 : {
								--z;
							}
							case 3 : {
								++z;
							}
							case 4 : {
								--x;
							}
							case 5 : {
								++x;
							}
						}
						if (fire) worldObj.setBlock(x, y, z, Blocks.fire);
					}
				}
			} else if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
				if (movingObjectPosition.entityHit != null) {
					doDamage(movingObjectPosition.entityHit);
					setFire(movingObjectPosition.entityHit);
				}
			}
			explode();
			setDead();
		}
	}

	private void setFire(Entity entity) {
		if (data != null) {
			if (data.getItemCount(NBTLaser.Items.Igniter) > 0) {
				entity.setFire((int) Math.ceil((double) data.getItemCount(NBTLaser.Items.Igniter) * 1.5));
			}
		}
	}

	private void doDamage(Entity entity) {
		if (data != null) {
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), getDamage());
		}
	}

	private float getDamage() {
		if (data.getItemCount(NBTLaser.Items.Phaser) == 0) return 0F;
		float perP = 1.5F;
		float perO = 1.2F;
		float total = 1F + perP * ((float) data.getItemCount(NBTLaser.Items.Phaser) + (perO * ((float) data.getItemCount(NBTLaser.Items.Overclock) + 1F)));
		return total;
	}

	private void explode() {
		if (data != null) {
			if (data.getItemCount(NBTLaser.Items.Explosion) > 0) {
				float expl = 1F + (0.325F * (float) data.getItemCount(NBTLaser.Items.Explosion)) + (0.1F * (float) data.getItemCount(NBTLaser.Items.Overclock));
				worldObj.createExplosion(this, posX, posY, posZ, expl, true);
			}
		}
	}

	public NBTLaser getLaserData() {
		return data;
	}

	public NBTTagCompound getNBT() {
		return stackTag;
	}

	private void addLight() {
		this.worldObj.setLightValue(EnumSkyBlock.Block, (int) this.posX, (int) this.posY, (int) this.posZ, 5);
		this.worldObj.markBlockRangeForRenderUpdate((int) this.posX, (int) this.posY, (int) this.posX, 12, 12, 12);
		this.worldObj.markBlockForUpdate((int) this.posX, (int) this.posY, (int) this.posZ);
		this.worldObj.updateLightByType(EnumSkyBlock.Block, (int) this.posX, (int) this.posY + 1, (int) this.posZ);
		this.worldObj.updateLightByType(EnumSkyBlock.Block, (int) this.posX, (int) this.posY - 1, (int) this.posZ);
		this.worldObj.updateLightByType(EnumSkyBlock.Block, (int) this.posX + 1, (int) this.posY, (int) this.posZ);
		this.worldObj.updateLightByType(EnumSkyBlock.Block, (int) this.posX - 1, (int) this.posY, (int) this.posZ);
		this.worldObj.updateLightByType(EnumSkyBlock.Block, (int) this.posX, (int) this.posY, (int) this.posZ + 1);
		this.worldObj.updateLightByType(EnumSkyBlock.Block, (int) this.posX, (int) this.posY, (int) this.posZ - 1);
	}
}
