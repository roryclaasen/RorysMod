package net.roryclaasen.rorysmod.entity;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.util.LaserData;

public class EntityLaser extends EntityThrowable {

	private LaserData data;
	private EntityLivingBase shootingEntity;

	private double damage;

	public EntityLaser(World world, EntityLivingBase entity, LaserData data) {
		super(world, entity); // SUPER! WOOOOOOORLD ENTITY!
		this.shootingEntity = entity;
		this.posY += entity.height / 2;
		this.data = data;
		damage = getDamage();
	}

	private double getDamage() {
		if (data.getPhaser() == 0) return 0.0;
		double perP = 1.12;
		double perO = 1.2;
		double total = (perP * (perO * data.getOverclock())) * data.getPhaser();
		return total;
	}

	private void explode() {
		if (data.getExplosion() > 0) {
			float expl = 0.325F * data.getExplosion();
			worldObj.createExplosion(this, posX, posY, posZ, expl, true);
		}
		setDead();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (data.getPhaser() > 0) updateDammage();
		if (ticksExisted > 20) {
			explode();
		}
	}

	@SuppressWarnings("rawtypes")
	private void updateDammage() {
		Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
		vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (movingobjectposition != null) {
			vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}

		Entity entity = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double d0 = 0.0D;
		int i;
		float f1;
		for (i = 0; i < list.size(); ++i) {
			Entity entity1 = (Entity) list.get(i);

			if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity)) {
				f1 = 0.3F;
				AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double) f1, (double) f1, (double) f1);
				MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

				if (movingobjectposition1 != null) {
					double d1 = vec31.distanceTo(movingobjectposition1.hitVec);
					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}
		if (entity != null) movingobjectposition = new MovingObjectPosition(entity);
		if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;
			if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
				movingobjectposition = null;
			}
		}
		float f2;

		if (movingobjectposition != null) {
			if (movingobjectposition.entityHit != null) {
				f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
				int k = MathHelper.ceiling_double_int((double) f2 * this.damage);

				DamageSource damagesource = null;

				if (this.shootingEntity == null) damagesource = new EntityDamageSourceIndirect("laser", this, this).setProjectile();
				else damagesource = new EntityDamageSourceIndirect("laser", this, this.shootingEntity).setProjectile();

				if (this.isBurning()) movingobjectposition.entityHit.setFire(5);

				if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float) k)) {
					if (movingobjectposition.entityHit instanceof EntityLivingBase) {
						if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
							((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
						}
					}
					this.setDead();
				}
			}
		}
	}

	@Override
	protected float getGravityVelocity() {
		return 0.005F;
	}

	@Override
	public void onImpact(MovingObjectPosition movingObjectPosition) {
		explode();
	}

	public LaserData getLaserData() {
		return data;
	}
}
