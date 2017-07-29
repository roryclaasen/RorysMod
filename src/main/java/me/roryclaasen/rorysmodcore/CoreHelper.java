/*
 * Copyright 2017 Rory Claasen
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
package me.roryclaasen.rorysmodcore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import cpw.mods.fml.relauncher.ReflectionHelper;
import me.roryclaasen.rorysmod.core.RorysConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CoreHelper {

	private CoreHelper() {}

	public static boolean shouldWakeUpNow() {
		return !RorysConfig.enableSleepInDay;
	}

	public static boolean shouldWakeUpNow(EntityPlayer player) {
		World world = player.worldObj;
		if (!world.isRemote) {
			if (RorysConfig.enableSleepInDay) return false;
			if (world.isDaytime()) return true;
		}
		return false;
	}

	public static void goodMorning(WorldServer world) {
		if (!world.isRemote) {
			if (RorysConfig.enableSleepInDay) {
				// TODO FIX THIS
				// world.setWorldTime(RorysConfig.sleepDayTime);
			} // else do nothing
		}
	}

	@SuppressWarnings("rawtypes")
	public static void notifyPlayers(WorldServer world) {
		if (RorysConfig.enableSleepInDay) {
			if (world.getWorldTime() == RorysConfig.sleepDayTime) {
				world.provider.resetRainAndThunder();

				Iterator iterator = world.playerEntities.iterator();
				while (iterator.hasNext()) {
					EntityPlayer entityplayer = (EntityPlayer) iterator.next();
					entityplayer.addChatMessage(new ChatComponentText(String.format(StatCollector.translateToLocal("message.rorysmod.sleeping.wakeup"), EnumChatFormatting.AQUA + entityplayer.getDisplayName() + EnumChatFormatting.WHITE)));
				}
			}
		} else {
			Method wakeAllPlayers = ReflectionHelper.findMethod(WorldServer.class, world, new String[] { "wakeAllPlayers", "func_73053_d" });
			try {
				wakeAllPlayers.invoke(world, new Object[] {});
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
