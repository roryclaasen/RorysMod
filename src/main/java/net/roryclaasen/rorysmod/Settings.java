package net.roryclaasen.rorysmod;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Settings {

	public static boolean enableMobsNearByCheck;
	public static boolean enableSleepInDay;
	public static boolean enableStayInBed;
	public static boolean bedText;

	private Configuration config;

	public Settings(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
	}

	/**
	 * @param event
	 */
	public void load(FMLPreInitializationEvent event) {
		FMLLog.log(RorysMod.MODID, Level.INFO, "Loading Config");
		config.load();

		enableMobsNearByCheck = config.get("sleeping", "enableMobsNearByCheck", false).getBoolean(false);
		enableSleepInDay = config.get("sleeping", "enableSleepInDay", true).getBoolean(true);
		enableStayInBed = config.get("sleeping", "stayInBed", true).getBoolean(true);
		bedText = config.get("sleeping", "whenAccessingBedShowNewText", false).getBoolean(false);

		config.save();
	}
}
