package net.roryclaasen.rorysmod.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.util.LaserData;

public class EntityLaser extends EntityThrowable {

	private LaserData data;

	public EntityLaser(World world) {
		super(world);
	}

	public EntityLaser(World world, EntityLivingBase entity, ItemStack itemStack) {
		super(world, entity);
		this.data = new LaserData(itemStack.stackTagCompound);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted > 20) {
			explode();
		}
	}

	@Override
	protected float getGravityVelocity() {
		return 0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition movingObjectPosition) {
		if (movingObjectPosition.entityHit != null) {
			movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), getDamage());
		}
		explode();
		if (!worldObj.isRemote) setDead();
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if (inGround && !worldObj.isRemote) {
			player.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), getDamage());
			setDead();
		}
	}

	private float getDamage() {
		if (data.getPhaser() == 0) return 0F;
		float perP = 1.12F;
		float perO = 1.2F;
		float total = (perP * (perO * data.getOverclock())) * data.getPhaser();
		return total;
	}

	private void explode() {
		if (data != null) {
			if (data.getExplosion() > 0) {
				float expl = 0.325F * data.getExplosion() + 1F;
				worldObj.createExplosion(this, posX, posY, posZ, expl, true);
			}
		}
	}

	public LaserData getLaserData() {
		return data;
	}
}
