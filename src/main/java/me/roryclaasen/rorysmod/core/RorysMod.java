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

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import me.roryclaasen.rorysmod.entity.EntityLaser;
import me.roryclaasen.rorysmod.entity.tile.TileEntityPoweredChest;
import me.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;
import me.roryclaasen.rorysmod.event.PlayerBedEventHandler;
import me.roryclaasen.rorysmod.event.PlayerHoldingRifle;
import me.roryclaasen.rorysmod.event.PlayerTickEvents;
import me.roryclaasen.rorysmod.gui.GuiHandler;
import me.roryclaasen.rorysmod.proxy.CommonProxy;
import me.roryclaasen.rorysmod.register.Register;
import me.roryclaasen.rorysmod.util.RMLog;

@Mod(modid = RorysMod.MODID, name = RorysMod.NAME, dependencies = "required-after:CoFHCore;before:IC2;")
public class RorysMod {

	@SidedProxy(clientSide = "me.roryclaasen.rorysmod.proxy.ClientProxy", serverSide = "me.roryclaasen.rorysmod.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final String MODID = "rorysmod";
	public static final String NAME = "Rory's Mod";

	@Instance(MODID)
	public static RorysMod instance;

	public static enum GUIS {
		RILE_TABLE("rorysmod.gui.upgradeTable"), CHEST_POWERED("rorysmod.gui.poweredchest");

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
				return ModItems.rifle1;
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

		RMLog.info("Registering Mod Recipes");
		blocks.createModRecipes();
		items.createModRecipes();
		
		RMLog.info("Registering everything else");

		Register.registerGUI(new GuiHandler());

		registerEventHandlers();
		registerTileEntities();
		registerModEntities();

		proxy.init(event);
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		
		RMLog.info("Registered " + Register.getRegisteredBlocks() + " block(s)");
		RMLog.info("Registered " + Register.getRegisteredItems() + " item(s)");
		RMLog.info("Registered " + Register.getRegisteredTileEntities() + " tile entity(s)");
		RMLog.info("Registered " + Register.getRegisteredEntities() + " entity(s)");
		RMLog.info("Registered " + Register.getRegisteredRecipies() + " recipie(s)");
		RMLog.info("Registered " + Register.getRegisteredEvents() + " event(s)");
	}

	private void registerTileEntities() {
		Register.registerTileEntity(TileEntityRifleTable.class, "tableUpgrade");
		Register.registerTileEntity(TileEntityPoweredChest.class, "blockChestPowered");
	}

	private void registerModEntities() {
		Register.registerEntities(EntityLaser.class, "laser", 64, 10, true);
	}

	private void registerEventHandlers() {
		Register.registerEventBus(new PlayerHoldingRifle());
		Register.registerEventBus(new PlayerTickEvents());
		Register.registerEventBus(new PlayerBedEventHandler());
	}
}
