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
package net.roryclaasen.rorysmod.event;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.roryclaasen.rorysmod.core.Settings;
import net.roryclaasen.rorysmod.util.RMLog;
import net.roryclaasen.rorysmod.util.ReflectionUtilities;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerBedEventHandler {

	private final String[] NOT_POSSIBLE_NOW = new String[]{"Getting in to bed right now is pointless", "Time wont wait for no one", "Sorry but you have to wait for night"};
	private final String[] NOT_SAFE = new String[]{"There are monsters near by!"};

	private final Random random = new Random();

	private final int sleepRange = 10;
	private static Field sleeping;
	private static Field sleeptimer;
	private static Method setsize;
	private static Method func_71013_b;

	@SuppressWarnings("rawtypes")
	public static void setupFields() {
		Class bed = EntityPlayer.class;
		Class entity = Entity.class;
		try {
			sleeping = ReflectionUtilities.getField("sleeping", "field_71083_bS", bed);
		} catch (Exception e) {
			RMLog.warn("Ran into error:\t" + e.getLocalizedMessage());
		}
		try {
			sleeptimer = ReflectionUtilities.getField("sleepTimer", "field_71076_b", bed);
		} catch (Exception e) {
			RMLog.warn("Ran into error:\t" + e.getLocalizedMessage());
		}
		try {
			setsize = ReflectionUtilities.getMethod("setSize", "func_70105_a", entity, float.class, float.class);
		} catch (Exception e) {
			RMLog.warn("Ran into error:\t" + e.getLocalizedMessage());
		}
		try {
			func_71013_b = ReflectionUtilities.getMethod("func_175139_a", "func_175139_a", bed, int.class);
		} catch (Exception e) {
			RMLog.warn("Ran into error:\t" + e.getLocalizedMessage());
		}
		try {
			func_71013_b.setAccessible(true);
		} catch (Exception e) {
			RMLog.warn("Ran into error:\t" + e.getLocalizedMessage());
		}
		try {
			setsize.setAccessible(true);
		} catch (Exception e) {
			RMLog.warn("Ran into error:\t" + e.getLocalizedMessage());
		}
		try {
			sleeping.setAccessible(true);
		} catch (Exception e) {
			RMLog.warn("Ran into error:\t" + e.getLocalizedMessage());
		}
		try {
			sleeptimer.setAccessible(true);
		} catch (Exception e) {
			RMLog.warn("Ran into error:\t" + e.getLocalizedMessage());
		}
	}

	@SubscribeEvent
	public void onWakeUpEvent(PlayerWakeUpEvent event) {
		RMLog.info("waking up");
		event.entity.worldObj.getWorldInfo().setWorldTime(1000);
		if (Settings.enableStayInBed) {
			// RMLog.info("Should be in bed");
		}
	}

	@SubscribeEvent
	public void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event) throws IllegalArgumentException, IllegalAccessException {
		EntityPlayer player = event.entityPlayer;
		List<EntityMob> list = getEntityMobFromPlayer(player, sleepRange);

		boolean mobs = (!Settings.enableMobsNearByCheck || list.isEmpty());
		boolean night = (Settings.enableSleepInDay || !event.entityPlayer.worldObj.isDaytime());

		if (mobs && night) {
			if (!event.entityPlayer.worldObj.isRemote) {
				if (event.entityPlayer.worldObj.isDaytime()) event.entityPlayer.addChatMessage(new ChatComponentText(getMessage(EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW)));
				if (!list.isEmpty()) event.entityPlayer.addChatMessage(new ChatComponentText(getMessage(EntityPlayer.EnumStatus.NOT_SAFE)));
			}
			event.result = EntityPlayer.EnumStatus.OK;
			if (sleeping != null) sleeping.setBoolean(player, true);
			if (sleeptimer != null) sleeptimer.setInt(player, 0);
			if (!player.worldObj.isRemote) {
				player.worldObj.updateAllPlayersSleepingFlag();
			}
			return;
		} else {
			if (!night) event.result = EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW;
			else if (!mobs && night) event.result = EntityPlayer.EnumStatus.NOT_SAFE;
			else event.result = EntityPlayer.EnumStatus.OTHER_PROBLEM;
			return;
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

	private String getMessage(EntityPlayer.EnumStatus status) {
		if (status == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
			int msg = random.nextInt(NOT_POSSIBLE_NOW.length);
			return NOT_POSSIBLE_NOW[msg];
		}
		if (status == EntityPlayer.EnumStatus.NOT_SAFE) {
			int msg = random.nextInt(NOT_SAFE.length);
			return NOT_SAFE[msg];
		}
		return "Message not Fo";
	}
}
