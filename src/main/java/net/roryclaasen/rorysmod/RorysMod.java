package net.roryclaasen.rorysmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.roryclaasen.rorysmod.data.ModBlocks;
import net.roryclaasen.rorysmod.data.ModItems;
import net.roryclaasen.rorysmod.data.Settings;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.event.PlayerBedEventHandler;
import net.roryclaasen.rorysmod.proxy.CommonProxy;
import net.roryclaasen.rorysmod.util.Arguments;
import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = RorysMod.MODID, name = RorysMod.NAME)
public class RorysMod {

	@SidedProxy(clientSide = "net.roryclaasen.rorysmod.proxy.ClientProxy", serverSide = "net.roryclaasen.rorysmod.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final String MODID = "rorysmod";
	public static final String NAME = "Rory's Mod";

	@Instance(MODID)
	private RorysMod instance;

	private Settings settings;

	public static ModBlocks blocks = new ModBlocks();
	public static ModItems items = new ModItems();

	public static CreativeTabs tab;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		settings = new Settings(event);
		settings.load(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {		
		tab = new CreativeTabs("rorysMobTab") {

			@Override
			public Item getTabIconItem() {
				return ModItems.rifle;
			}
		};
		
		blocks.init(event);
		items.init(event);

		addRecipes();
		register(event);
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {}

	private void register(FMLInitializationEvent event) {
		registerEventHandlers();
		proxy.init(event);
		EntityRegistry.registerModEntity(EntityLaser.class, "LASER", 0, this, 64, 10, true);
	}

	private void addRecipes() {
		RMLog.info("Registering Recipes");
		blocks.createRecipes();
		items.createRecipes();
	}

	private void registerEventHandlers() {
		RMLog.info("Registering events");

		if (Arguments.isExperiment()) {
			MinecraftForge.EVENT_BUS.register(new PlayerBedEventHandler());
		} else RMLog.info("Skiping expiremntal Handlers");
	}
}
