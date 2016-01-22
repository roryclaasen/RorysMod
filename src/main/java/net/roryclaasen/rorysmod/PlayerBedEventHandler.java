package net.roryclaasen.rorysmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerBedEventHandler {

	private int sleepRange = 10;

	@SubscribeEvent
	public void onWakeUpEvent(PlayerWakeUpEvent event) {
		//RMLog.info("waking up");
		if (Settings.shouldStayInBed()) {
		//	RMLog.info("should stay in bed");
		}
	}

	@SubscribeEvent
	public void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event) {
		// RMLog.info("PlayerSleepInBedEvent");
		List<EntityMob> list = getEntityMobFromPlayer(event.entityPlayer, sleepRange);

		boolean mobs = (!Settings.isEnableMobsNearByCheck() || list.isEmpty());
		boolean night = (Settings.isEnableSleepInDay() || !event.entityPlayer.worldObj.isDaytime());
		// RMLog.info("mobs:" + mobs);
		// RMLog.info("night:" + night);
		if (mobs && night) {
			// RMLog.info("Sleep is allowed");
			// TickHandler.playerRequesting = event.entityPlayer;
			// TickHandler.nearbyMobList = list;
		}
	}

	@SuppressWarnings("unchecked")
	private List<EntityMob> getEntityMobFromPlayer(EntityLivingBase player, int range) {
		double px = player.posX;
		double py = player.posY;
		double pz = player.posZ;
		List<Entity> l = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(px - range, py - range, pz - range, px + range, py + range, pz + range));
		List<EntityMob> result = new ArrayList<EntityMob>();
		for (int i = 0; i < l.size(); ++i) {
			EntityLivingBase x = (EntityLivingBase) l.get(i);
			if (x != null) {
				if (x instanceof EntityMob) {
					if (x.getDistanceToEntity(player) <= range) {
						result.add((EntityMob) x);
					}
				}
			}
		}
		return result;
	}
}
