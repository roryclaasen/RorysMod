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
package me.roryclaasen.rorysmod.core;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Settings {

	public static boolean enableMobsNearByCheck;
	public static boolean enableSleepInDay;
	public static boolean enableStayInBed;
	public static boolean showColorBox;
	public static boolean laserEmitsLight;

	public static boolean coloredLaser;
	public static boolean laserTooltip;
	public static int rifleTier1;
	public static int rifleTier2;
	public static int rifleTier3;
	public static int rifleTier4;
	public static int rifleTier5;
	public static boolean setFireToBlocks;

	private static Configuration config;

	public Settings(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
	}

	public void load(FMLPreInitializationEvent event) {
		FMLLog.log(RorysMod.MODID, Level.INFO, "Loading Config");
		config.load();

		updateSettings();
	}

	public void updateSettings() {
		// sleeping
		enableMobsNearByCheck = config.get("sleeping", "enableMobsNearByCheck", false, "If enabled then you can't sleep with monsters near by").getBoolean(false);
		enableSleepInDay = config.get("sleeping", "enableSleepInDay", true, "If enabled then you can sleep in the day").getBoolean(true);
		// enableStayInBed = config.get("sleeping", "stayInBed", true, "You will not get kicked out of the bed in daytime").getBoolean(true);

		// modular lasers
		coloredLaser = config.get("modular-lasers", "allowColouredLasers", true).getBoolean(true);
		laserTooltip = config.get("modular-lasers", "showLaserTooltips", true, "Allow modules to be shown under the rifle tooltip").getBoolean(true);
		laserEmitsLight = config.get("modular-lasers", "laserEmitsLight", false, "laserEmitsLight is an experimental feature").getBoolean(false);
		setFireToBlocks = config.get("modular-lasers", "setFireToBlocks", true, "Lasers will set fire on collision").getBoolean(true);

		// rifle tiers
		rifleTier1 = config.getInt("rifleTier1MaxWeight", "modular-lasers", 8, 1, 97, "Rifle Tier 1 Max Weight");
		rifleTier2 = config.getInt("rifleTier2MaxWeight", "modular-lasers", 16, 1, 97, "Rifle Tier 2 Max Weight");
		rifleTier3 = config.getInt("rifleTier3MaxWeight", "modular-lasers", 24, 1, 97, "Rifle Tier 3 Max Weight");
		rifleTier4 = config.getInt("rifleTier4MaxWeight", "modular-lasers", 32, 1, 97, "Rifle Tier 4 Max Weight");
		rifleTier5 = config.getInt("rifleTier5MaxWeight", "modular-lasers", 40, 1, 97, "Rifle Tier 5 Max Weight");

		// GUI
		showColorBox = config.get("gui", "showColorBox", false, "Shows a square with the color of the laser").getBoolean(false);

		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (RorysMod.MODID.equals(event.modID)) updateSettings();
	}

	public static Configuration getConfig() {
		return config;
	}
}
