package net.roryclaasen.rorysmod;

import net.minecraftforge.common.MinecraftForge;
import net.roryclaasen.rorysmod.event.PlayerBedEventHandler;
import net.roryclaasen.rorysmod.proxy.ProxyCommon;
import net.roryclaasen.rorysmod.util.BlockRegistry;
import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RorysMod.MODID, version = RorysMod.VERSION, name = RorysMod.NAME)
public class RorysMod {

	@SidedProxy(clientSide = "net.roryclaasen.rorysmod.proxy.ProxyClient", serverSide = "net.roryclaasen.rorysmod.proxy.ProxyCommon")
	public static ProxyCommon proxy;

	public static final String MODID = "rorysmod";
	public static final String NAME = "Rory's Mod";
	public static final String VERSION = "1.0";

	private Settings settings;

	public BlockRegistry blocks;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		settings = new Settings(event);
		settings.load(event);

		blocks = new BlockRegistry();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		RMLog.info("Initializing mod!");

		addItems();
		addBlocks();
		registerEventHandlers();

		addRecipes();
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {}

	private void addItems() {}

	private void addRecipes() {}

	private void addBlocks() {}

	private void registerEventHandlers() {
		RMLog.info("Registering events");

		if (Settings.isExperiment()) {
			MinecraftForge.EVENT_BUS.register(new PlayerBedEventHandler());
		}
	}
}
