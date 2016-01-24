package net.roryclaasen.rorysmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.roryclaasen.rorysmod.event.PlayerBedEventHandler;
import net.roryclaasen.rorysmod.proxy.ProxyCommon;
import net.roryclaasen.rorysmod.util.Arguments;
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
	
	public static CreativeTabs tab = new CreativeTabs("rorysMobTab") {
		
		@Override
		public Item getTabIconItem() {
			return Items.book;
		}
	};

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

	private void addItems() {
		RMLog.info("Registering Items");}

	private void addRecipes() {
		RMLog.info("Registering Recipes");}

	private void addBlocks() {
		RMLog.info("Registering Blocks");}

	private void registerEventHandlers() {
		RMLog.info("Registering events");

		if (Arguments.isExperiment()) {
			MinecraftForge.EVENT_BUS.register(new PlayerBedEventHandler());
		} else RMLog.info("Skiiping expiremntal Handlers");
	}
}
