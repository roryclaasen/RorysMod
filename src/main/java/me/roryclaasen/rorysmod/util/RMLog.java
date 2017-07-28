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

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import me.roryclaasen.rorysmod.core.RorysGlobal;

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
		if (object == null) object = "null";
		if (core) FMLLog.log(RorysGlobal.MODID + "core", level, object.toString());
		else FMLLog.log(RorysGlobal.MODID, level, object.toString());
	}
}
