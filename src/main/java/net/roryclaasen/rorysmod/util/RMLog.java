package net.roryclaasen.rorysmod.util;

import net.roryclaasen.rorysmod.RorysMod;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class RMLog {

	public static void info(Object object) {
		info(object, false);
	}

	public static void info(Object object, boolean core) {
		log(Level.INFO, object, core);
	}

	public static void warn(Object object) {
		warn(object, false);
	}

	public static void warn(Object object, boolean core) {
		log(Level.WARN, object, core);
	}

	public static void error(Object object) {
		error(object, false);
	}

	public static void error(Object object, boolean core) {
		log(Level.ERROR, object, core);
	}

	public static void fatal(Object object) {
		fatal(object, false);
	}

	public static void fatal(Object object, boolean core) {
		log(Level.FATAL, object, core);
	}

	public static void log(Level level, Object object, boolean core) {
		if (core) FMLLog.log(RorysMod.MODID + "core", level, object.toString());
		else FMLLog.log(RorysMod.MODID, level, object.toString());
	}
}
