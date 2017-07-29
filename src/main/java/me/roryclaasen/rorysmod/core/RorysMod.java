/*
 * Copyright 2016-2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmod.core;

import java.util.Random;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import me.roryclaasen.rorysmod.block.tile.TileEntityPoweredChest;
import me.roryclaasen.rorysmod.block.tile.TileEntityRenamer;
import me.roryclaasen.rorysmod.block.tile.TileEntityRifleTable;
import me.roryclaasen.rorysmod.core.init.ModBlocks;
import me.roryclaasen.rorysmod.core.init.ModItems;
import me.roryclaasen.rorysmod.core.intergrations.MekanismPlugin;
import me.roryclaasen.rorysmod.core.network.PacketDispatcher;
import me.roryclaasen.rorysmod.core.network.proxy.CommonProxy;
import me.roryclaasen.rorysmod.core.recipe.RoryChargedShapedRecipe;
import me.roryclaasen.rorysmod.core.recipe.RoryChargedShapelessRecipe;
import me.roryclaasen.rorysmod.core.recipe.RoryShapedRecipe;
import me.roryclaasen.rorysmod.core.recipe.RoryShapelessRecipe;
import me.roryclaasen.rorysmod.core.register.Register;
import me.roryclaasen.rorysmod.entity.EntityLaser;
import me.roryclaasen.rorysmod.event.PlayerBedEventHandler;
import me.roryclaasen.rorysmod.event.PlayerHoldingRifle;
import me.roryclaasen.rorysmod.event.PlayerTickEvents;
import me.roryclaasen.rorysmod.gui.GuiHandler;
import me.roryclaasen.rorysmod.util.RMLog;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.RecipeSorter;

@Mod(modid = RorysGlobal.MODID, name = RorysGlobal.NAME, version = RorysGlobal.VERSION, dependencies = "after:CoFHCore;after:IC2;")
public class RorysMod {

	@SidedProxy(clientSide = "me.roryclaasen.rorysmod.core.network.proxy.ClientProxy", serverSide = "me.roryclaasen.rorysmod.core.network.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static Random random;

	@Instance(RorysGlobal.MODID)
	public static RorysMod instance;

	public static enum GUIS {
		RILE_TABLE("rorysmod.gui.upgradeTable"), CHEST_POWERED("rorysmod.gui.poweredchest"), MACHINE_RENAMER("rorysmod.gui.machineRenamer");

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

	private RorysConfig settings;

	public static ModBlocks blocks;
	public static ModItems items;

	public static CreativeTabs creativeTab;

	public Version versionCheker;

	static {
		random = new Random();

		blocks = new ModBlocks();
		items = new ModItems();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;

		versionCheker = new Version(RorysGlobal.VERSION);

		settings = new RorysConfig(event);
		settings.load(event);

		creativeTab = new CreativeTabs("rorysMobTab") {
			@Override
			public Item getTabIconItem() {
				return RorysMod.items.rifle1;
			}
		};

		blocks.preInit(event);
		items.preInit(event);

		blocks.register(event);
		items.register(event);

		PacketDispatcher.registerPackets();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		RMLog.info("Registering Recipes");
		blocks.init(event);
		items.init(event);

		RMLog.info("Registering everything else");

		Register.registerGUI(new GuiHandler());

		registerEventHandlers();
		registerTileEntities();
		registerModEntities();

		proxy.init(event);
	}

	private void registerTileEntities() {
		Register.registerTileEntity(TileEntityRifleTable.class, "tableUpgrade");
		Register.registerTileEntity(TileEntityPoweredChest.class, "blockChestPowered");
		Register.registerTileEntity(TileEntityRenamer.class, "machineRenamer");
	}

	private void registerModEntities() {
		Register.registerEntities(EntityLaser.class, "laser", 64, 10, true);
	}

	private void registerEventHandlers() {
		Register.registerEventBus(new PlayerHoldingRifle());
		Register.registerEventBus(new PlayerTickEvents());
		Register.registerEventBus(new PlayerBedEventHandler());
	}

	private void registerRecipieSorter() {
		RecipeSorter.register(RorysGlobal.MODID + ":shaped", RoryShapedRecipe.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register(RorysGlobal.MODID + ":shapeless", RoryShapelessRecipe.class, RecipeSorter.Category.SHAPELESS, "after:" + RorysGlobal.MODID + ":shaped");
		RecipeSorter.register(RorysGlobal.MODID + ":shapedCharge", RoryChargedShapedRecipe.class, RecipeSorter.Category.SHAPED, "after:" + RorysGlobal.MODID + ":shapeless");
		RecipeSorter.register(RorysGlobal.MODID + ":shapelessCharge", RoryChargedShapelessRecipe.class, RecipeSorter.Category.SHAPELESS, "after:" + RorysGlobal.MODID + ":shapedCharge");
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		blocks.postinit(event);
		items.postinit(event);

		registerRecipieSorter();
		
		MekanismPlugin.load();

		RMLog.info("Registered " + Register.getRegisteredBlocks() + " block(s)");
		RMLog.info("Registered " + Register.getRegisteredItems() + " item(s)");
		RMLog.info("Registered " + Register.getRegisteredTileEntities() + " tile entity(s)");
		RMLog.info("Registered " + Register.getRegisteredEntities() + " entity(s)");
		RMLog.info("Registered " + Register.getRegisteredRecipies() + " recipie(s)");
		RMLog.info("Registered " + Register.getRegisteredEvents() + " event(s)");
		RMLog.info("Registered " + PacketDispatcher.getPacketId() + " packet(s)");

		Thread check = new Thread(versionCheker, RorysGlobal.MODID + " Version Check");
		check.start();
	}
}
