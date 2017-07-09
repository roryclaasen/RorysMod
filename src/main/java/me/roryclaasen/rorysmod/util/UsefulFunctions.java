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
package me.roryclaasen.rorysmod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class UsefulFunctions {

	public static Boolean isServer() {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER) return true;
		else if (side == Side.CLIENT) return false;
		else RMLog.warn("Server returned no side!");
		return null;
	}

	public static boolean isPlayer(Entity entity) {
		if (entity instanceof EntityPlayer) return true;
		else return false;
	}

	public static EntityPlayer getPlayerFromEntity(Entity entity) {
		if (isPlayer(entity)) return (EntityPlayer) entity;
		else return null;
	}
}
