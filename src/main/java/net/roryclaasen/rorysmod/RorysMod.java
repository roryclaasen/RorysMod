package net.roryclaasen.rorysmod;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RorysMod.MODID, version = RorysMod.VERSION)
public class RorysMod {

	public static final String MODID = "rorysmod";
	public static final String VERSION = "1.0";

	private Settings settings;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		settings = new Settings(event);
		settings.load(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		FMLLog.log(RorysMod.MODID, Level.INFO, "Loading mod!");

		MinecraftForge.EVENT_BUS.register(new PlayerBedEventHandler());
	}

}
