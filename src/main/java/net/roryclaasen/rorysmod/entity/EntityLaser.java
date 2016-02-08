package net.roryclaasen.rorysmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.core.Settings;
import net.roryclaasen.rorysmod.util.NBTLaser;
import net.roryclaasen.rorysmod.util.RMLog;

public class EntityLaser extends EntityThrowable {

	private NBTLaser data;

	public EntityLaser(World world) {
		super(world);
	}

	public EntityLaser(World world, EntityLivingBase entity, ItemStack itemStack) {
		super(world, entity);
		this.data = new NBTLaser(itemStack.stackTagCompound);
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
		if (movingObjectPosition.entityHit != null) {
			doDamage(movingObjectPosition.entityHit);
		}
		explode();
		/* if (!worldObj.isRemote) */setDead();
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if (inGround && !worldObj.isRemote) {
			doDamage(player);
			setDead();
		}
	}

	private void doDamage(Entity entity) {
		if (data != null) {
			RMLog.info(getDamage());
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), getDamage());
		}
	}

	private float getDamage() {
		if (data.getItemCount(NBTLaser.Items.Phaser) == 0) return 0F;
		float perP = 1.5F;
		float perO = 1.2F;
		float total = 1F + (perP * (data.getItemCount(NBTLaser.Items.Phaser) * ((perO * (data.getItemCount(NBTLaser.Items.Overclock) + 1)))));
		return total;
	}

	private void explode() {
		if (data != null) {
			if (data.getItemCount(NBTLaser.Items.Explosion) > 0) {
				float expl = 0.325F * data.getItemCount(NBTLaser.Items.Explosion) + 1F;
				worldObj.createExplosion(this, posX, posY, posZ, expl, true);
			}
		}
	}

	public NBTLaser getNBT() {
		return data;
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
