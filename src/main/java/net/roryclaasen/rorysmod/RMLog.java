package net.roryclaasen.rorysmod;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class RMLog {

	public static void info(String string) {
		log(Level.INFO, string);
	}

	public static void warn(String string) {
		log(Level.WARN, string);
	}

	public static void error(String string) {
		log(Level.ERROR, string);
	}

	public static void fatal(String string) {
		log(Level.FATAL, string);
	}

	public static void log(Level level, String string) {
		FMLLog.log(RorysMod.MODID, level, string);
	}
}
