package net.roryclaasen.rorysmod;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Settings {

	private static boolean enableMobsNearByCheck;
	private static boolean enableSleepInDay;

	private Configuration config;

	public Settings(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
	}

	/**
	 * @param event
	 */
	public void load(FMLPreInitializationEvent event) {
		FMLLog.log(RorysMod.MODID, Level.INFO, "Loading Config");

		enableMobsNearByCheck = config.get("sleeping", "enableMobsNearByCheck", false).getBoolean(false);
		enableSleepInDay = config.get("sleeping", "enableSleepInDay", true).getBoolean(true);
		config.save();
	}

	public static boolean isEnableMobsNearByCheck() {
		return enableMobsNearByCheck;
	}

	public static boolean isEnableSleepInDay() {
		return enableSleepInDay;
	}
}
