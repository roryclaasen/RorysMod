package net.roryclaasen.rorysmod.event;

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
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerBedEventHandler {

	private final String[] NOT_POSSIBLE_NOW = new String[]{"Getting in to bed right now is pointless", "Time wont wait for no one", "Sorry but you have to wait for night"};
	private final String[] NOT_SAFE = new String[]{"There are monsters near by!"};

	private final Random random = new Random();

	private final int sleepRange = 10;

	@SubscribeEvent
	public void onWakeUpEvent(PlayerWakeUpEvent event) {
		RMLog.info("waking up");
		if (Settings.enableStayInBed) {
			// RMLog.info("Should be in bed");
		}
	}

	@SubscribeEvent
	public void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event) {
		List<EntityMob> list = getEntityMobFromPlayer(event.entityPlayer, sleepRange);

		boolean mobs = (!Settings.enableMobsNearByCheck || list.isEmpty());
		boolean night = (Settings.enableSleepInDay || !event.entityPlayer.worldObj.isDaytime());

		if (mobs && night) {
			RMLog.info("Player can sleep");
			if (Settings.bedText) {
				if (event.entityPlayer.worldObj.isDaytime()) event.entityPlayer.addChatMessage(new ChatComponentText(getMessage(EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW)));
				if (!list.isEmpty()) event.entityPlayer.addChatMessage(new ChatComponentText(getMessage(EntityPlayer.EnumStatus.NOT_SAFE)));
			}

			//event.result = EntityPlayer.EnumStatus.OK;
		} else {
			if (!night) event.result = EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW;
			else if (!mobs && night) event.result = EntityPlayer.EnumStatus.NOT_SAFE;
			else event.result = EntityPlayer.EnumStatus.OTHER_PROBLEM;
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
