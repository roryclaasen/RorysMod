/*
 * Copyright 2016-2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmod.event;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import me.roryclaasen.rorysmod.core.Settings;

public class PlayerBedEventHandler {

	@SubscribeEvent
	public void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event) throws IllegalArgumentException, IllegalAccessException {
		EntityPlayer player = event.entityPlayer;
		World worldObj = player.worldObj;

		if (!worldObj.isRemote) {
			if (player.isPlayerSleeping() || !player.isEntityAlive()) {
				event.result = EntityPlayer.EnumStatus.OTHER_PROBLEM;
				return;
			}

			if (!worldObj.provider.isSurfaceWorld()) {
				event.result = EntityPlayer.EnumStatus.NOT_POSSIBLE_HERE;
				return;
			}

			if (worldObj.isDaytime() && !Settings.enableSleepInDay) {
				event.result = EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW;
				return;
			}

			if (Math.abs(player.posX - (double) event.x) > 3.0D || Math.abs(player.posY - (double) event.y) > 2.0D || Math.abs(player.posZ - (double) event.z) > 3.0D) {
				event.result = EntityPlayer.EnumStatus.TOO_FAR_AWAY;
				return;
			}

			double d0 = 8.0D;
			double d1 = 5.0D;
			@SuppressWarnings("rawtypes")
			List list = worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox((double) event.x - d0, (double) event.y - d1, (double) event.z - d0, (double) event.x + d0, (double) event.y + d1, (double) event.z + d0));

			if (!list.isEmpty()) {
				if (Settings.enableMobsNearByCheck) {
					event.result = EntityPlayer.EnumStatus.NOT_SAFE;
					return;
				} else {
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.sleeping.mobs")));
				}
			}
		}

		if (player.isRiding()) {
			player.mountEntity((Entity) null);
		}

		player.setSize(0.2F, 0.2F);
		player.yOffset = 0.2F;

		if (worldObj.blockExists(event.x, event.y, event.z)) {
			int l = worldObj.getBlock(event.x, event.y, event.z).getBedDirection(worldObj, event.x, event.y, event.z);
			float f1 = 0.5F;
			float f = 0.5F;

			switch (l) {
				case 0:
					f = 0.9F;
					break;
				case 1:
					f1 = 0.1F;
					break;
				case 2:
					f = 0.1F;
					break;
				case 3:
					f1 = 0.9F;
			}

			func_71013_b(player, l);
			player.setPosition((double) ((float) event.x + f1), (double) ((float) event.y + 0.9375F), (double) ((float) event.z + f));
		} else {
			player.setPosition((double) ((float) event.x + 0.5F), (double) ((float) event.y + 0.9375F), (double) ((float) event.z + 0.5F));
		}

		player.sleeping = true;
		player.sleepTimer = 0;
		player.playerLocation = new ChunkCoordinates(event.x, event.y, event.z);
		player.motionX = player.motionZ = player.motionY = 0.0D;

		if (!worldObj.isRemote) {
			worldObj.updateAllPlayersSleepingFlag();
			if (worldObj.isDaytime()) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.sleeping.daytime")));
			else {
				// TODO msg x amount of player are sleeping
			}
		}
		event.result = EntityPlayer.EnumStatus.OK;
	}

	private void func_71013_b(EntityPlayer player, int p_71013_1_) {
		player.field_71079_bU = 0.0F;
		player.field_71089_bV = 0.0F;

		switch (p_71013_1_) {
			case 0:
				player.field_71089_bV = -1.8F;
				break;
			case 1:
				player.field_71079_bU = 1.8F;
				break;
			case 2:
				player.field_71089_bV = 1.8F;
				break;
			case 3:
				player.field_71079_bU = -1.8F;
		}
	}
}
