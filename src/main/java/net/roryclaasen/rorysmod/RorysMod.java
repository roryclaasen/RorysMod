package net.roryclaasen.rorysmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = RorysMod.MODID, version = RorysMod.VERSION)
public class RorysMod {

	public static final String MODID = "rorysmod";
	public static final String VERSION = "1.0";

	@EventHandler
	public void init(FMLInitializationEvent event) {}
}
