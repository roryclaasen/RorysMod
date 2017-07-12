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
package me.roryclaasen.rorysmodcore.asm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javatuples.Triplet;

import me.roryclaasen.rorysmod.util.RMLog;

public final class ASMNames {
	private static final Map<String, String> MAPPINGS = new HashMap<>();

	static final Pattern OWNERNAME = Pattern.compile("(\\S*)/(.*)");

	public static final String MD_PLAYER_UPDATE = "net/minecraft/entity/player/EntityPlayer/onUpdate ()V";
	public static final String MD_PLAYER_SLEEP_IN_BED = "net/minecraft/entity/player/EntityPlayer/sleepInBedAt (III)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;";
	public static final String MD_ENTITY_LIVING_UPDATE = "net/minecraft/entity/EntityLivingBase/onUpdate ()V";
	
	public static final String MD_WORLD_IS_DAY = "net/minecraft/world/World/isDaytime ()Z";
	public static final String MD_WAKE_ALL_PLAYERS = "net/minecraft/world/WorldServer/wakeAllPlayers ()V";

	public static final String MD_WORLD_TICK = "net/minecraft/world/WorldServer/tick ()V";
	
	public static final String MD_RM_HELPER_SLEEP = "me/roryclaasen/rorysmodcore/CoreHelper/shouldWakeUpNow Z";
	public static final String MD_RM_HELPER_SLEEP_PLAEYR = "me/roryclaasen/rorysmodcore/CoreHelper/shouldWakeUpNow (Lnet/minecraft/entity/player/EntityPlayer;)Z";

	public static final String FD_PLAYER_WORLD_OBJ = "net/minecraft/entity/player/EntityPlayer/worldObj Lnet/minecraft/world/World;";
	public static final String FD_WORLD_IS_REMOTE = "net/minecraft/world/World/isRemote Z";
	
	public static final String FD_PLAYER_ENUM_NOT_POSSIBLE = "net/minecraft/entity/player/EntityPlayer$EnumStatus/NOT_POSSIBLE_HERE Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;";
	public static final String FD_PLAYER_ENUM_OK = "net/minecraft/entity/player/EntityPlayer$EnumStatus/OK Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;";
	
	static {
		MAPPINGS.put(MD_PLAYER_UPDATE, "func_70071_h_");
		MAPPINGS.put(MD_PLAYER_SLEEP_IN_BED, "func_71018_a");
		MAPPINGS.put(MD_ENTITY_LIVING_UPDATE, "func_70071_h_");

		MAPPINGS.put(MD_WORLD_IS_DAY, "func_72935_r");
		MAPPINGS.put(MD_WAKE_ALL_PLAYERS, "func_73053_d");
		
		MAPPINGS.put(MD_WORLD_TICK, "func_72835_b");
		
		MAPPINGS.put(FD_PLAYER_WORLD_OBJ, "field_70170_p");
		MAPPINGS.put(FD_WORLD_IS_REMOTE, "field_72995_K");

		MAPPINGS.put(FD_PLAYER_ENUM_NOT_POSSIBLE, "field_72995_K");
		MAPPINGS.put(FD_PLAYER_ENUM_OK, "field_72995_K");
	}

	public static Triplet<String, String, String[]> getSrgNameMd(String method) {
		Matcher mtch = OWNERNAME.matcher(method);
		if (!mtch.find()) {
			RMLog.fatal("Method string does not match pattern!", true);
			throw new RuntimeException("SRG-Name not found!");
		}

		String srgName = ASMHelper.isMCP ? null : MAPPINGS.get(method);

		String owner = mtch.group(1);
		String[] splitMd = mtch.group(2).split(" ");

		String name = srgName == null ? splitMd[0] : srgName;
		String[] additData = Arrays.copyOfRange(splitMd, 1, splitMd.length);

		return Triplet.with(owner, name, additData);
	}

	public static Triplet<String, String, String> getSrgNameFd(String field) {
		Matcher mtch = OWNERNAME.matcher(field);
		if (!mtch.find()) {
			RMLog.fatal("Field string does not match pattern!", true);
			throw new RuntimeException("SRG-Name not found!");
		}

		String srgName = ASMHelper.isMCP ? null : MAPPINGS.get(field);

		String owner = mtch.group(1);
		String[] splitFd = mtch.group(2).split(" ");

		String name = srgName == null ? splitFd[0] : srgName;
		String desc = splitFd[1];

		return Triplet.with(owner, name, desc);
	}
}
