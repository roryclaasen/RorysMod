package net.roryclaasen.rorysmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.roryclaasen.rorysmod.core.ModBlocks;
import net.roryclaasen.rorysmod.core.ModItems;
import net.roryclaasen.rorysmod.core.Settings;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;
import net.roryclaasen.rorysmod.event.PlayerBedEventHandler;
import net.roryclaasen.rorysmod.gui.GuiHandler;
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
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = RorysMod.MODID, name = RorysMod.NAME, dependencies = "after:IC2")
public class RorysMod {

	@SidedProxy(clientSide = "net.roryclaasen.rorysmod.proxy.ClientProxy", serverSide = "net.roryclaasen.rorysmod.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final String MODID = "rorysmod";
	public static final String NAME = "Rory's Mod";

	@Instance(MODID)
	public static RorysMod instance;

	public static enum GUIS {
		RILE_TABLE("rorysmod.gui.upgradeTable");

		private String name;

		GUIS(String name) {
			this.name = name;
		}

		public int getId() {
			return this.ordinal();
		}

		public String getName() {
			return name;
		}
	}

	private Settings settings;

	public static ModBlocks blocks = new ModBlocks();
	public static ModItems items = new ModItems();

	public static CreativeTabs tab;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		settings = new Settings(event);
		settings.load(event);

		tab = new CreativeTabs("rorysMobTab") {

			@Override
			public Item getTabIconItem() {
				return ModItems.rifle;
			}
		};

		blocks.preInit(event);
		items.preInit(event);

		blocks.register(event);
		items.register(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		RMLog.info("Registering Recipes");
		blocks.createRecipes();
		items.createRecipes();
		RMLog.info("Registering everything else");

		registerEventHandlers();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		proxy.init(event);

		GameRegistry.registerTileEntity(TileEntityRifleTable.class, "RifleTable");
		EntityRegistry.registerModEntity(EntityLaser.class, "laser", 0, RorysMod.instance, 64, 10, true);
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {}

	private void registerEventHandlers() {
		if (Arguments.isExperiment()) {
			MinecraftForge.EVENT_BUS.register(new PlayerBedEventHandler());
		} else RMLog.info("Skiping expiremntal Handlers");
	}
}
