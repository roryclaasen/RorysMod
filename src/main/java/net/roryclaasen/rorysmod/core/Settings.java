package net.roryclaasen.rorysmod.core;

import net.minecraftforge.common.config.Configuration;
import net.roryclaasen.rorysmod.RorysMod;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Settings {

	public static boolean enableMobsNearByCheck;
	public static boolean enableSleepInDay;
	public static boolean enableStayInBed;
	public static boolean bedText;

	public static boolean coloredLaser;
	public static boolean laserTooltip;
	public static int rifleTier1 = 8;
	public static int rifleTier2 = 16;
	public static int rifleTier3 = 24;
	public static int rifleTier4 = 32;
	public static int rifleTier5 = 40;

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
		enableMobsNearByCheck = config.get("sleeping", "enableMobsNearByCheck", false).getBoolean(false);
		enableSleepInDay = config.get("sleeping", "enableSleepInDay", true).getBoolean(true);
		enableStayInBed = config.get("sleeping", "stayInBed", true).getBoolean(true);
		bedText = config.get("sleeping", "whenAccessingBedShowNewText", false).getBoolean(false);

		// modular lasers
		coloredLaser = config.get("modular-lasers", "allowColouredLasers", true).getBoolean(true);
		laserTooltip = config.get("modular-lasers", "showLaserModualsl", true).getBoolean(true);

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
