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
package me.roryclaasen.rorysmod.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import me.roryclaasen.rorysmod.block.BlockBaseMeta;
import me.roryclaasen.rorysmod.block.BlockBlueprint;
import me.roryclaasen.rorysmod.block.BlockIngot;
import me.roryclaasen.rorysmod.block.BlockPoweredChest;
import me.roryclaasen.rorysmod.block.BlockRifleTable;
import me.roryclaasen.rorysmod.block.BlockTestingWall;
import me.roryclaasen.rorysmod.block.MultiBlockHandler;
import me.roryclaasen.rorysmod.item.ItemPoweredChest;
import me.roryclaasen.rorysmod.register.Register;
import me.roryclaasen.rorysmod.util.RMLog;
import me.roryclaasen.rorysmod.util.registry.BlockRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ModBlocks implements TypeGroup {

	public BlockRegistry registry = new BlockRegistry();

	public static Block testingWall;
	public static Block upgradeTable;
	public static Block steelBlock;
	public static Block bluePrint;

	public static Block poweredChest;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		testingWall = new BlockTestingWall(Material.iron, "blockTest");
		upgradeTable = new BlockRifleTable(Material.anvil, "tableUpgrade");
		steelBlock = new BlockIngot(Material.iron, "blockSteel");
		bluePrint = new BlockBlueprint(Material.iron, "blockBluePrint");

		poweredChest = new BlockPoweredChest(Material.wood, "blockChestPowered");
	}

	@Override
	public void register(FMLPreInitializationEvent event) {
		RMLog.info("Registering Blocks");
		Register.registerBlock(testingWall, MultiBlockHandler.class);
		Register.registerBlock(upgradeTable);
		Register.registerBlock(steelBlock);
		Register.registerBlock(bluePrint, MultiBlockHandler.class);

		Register.registerDictionary("blockSteel", steelBlock);
		for (int i = 0; i < ((BlockBaseMeta) testingWall).getMetaSize(); i++) {
			Register.registerDictionary("testingWall", new ItemStack(testingWall, 1, i));
		}
		for (int i = 0; i < ((BlockBaseMeta) bluePrint).getMetaSize(); i++) {
			Register.registerDictionary("bluePrint", new ItemStack(bluePrint, 1, i));
		}

		Register.registerBlock(poweredChest, ItemPoweredChest.class);
	}

	@Override
	public void createRecipes() {
		Register.addShapedRecipie(new ItemStack(steelBlock), new Object[]{"sss", "sss", "sss", 's', "ingotSteel"});
		for (int id = 0; id < ((BlockBaseMeta) testingWall).getMetaSize(); id++) {
			ItemStack dye = new ItemStack(Items.dye, 1, 15 - id);
			ItemStack currentBlock = new ItemStack(testingWall, 1, id);
			Register.addShapedRecipie(currentBlock, new Object[]{" i ", "idi", " i ", 'i', "ingotSteel", 'd', dye});
			Register.addShaplessRecipie(currentBlock, new Object[]{"testingWall", dye, dye, dye});
		}
		Register.addShapedRecipie(new ItemStack(upgradeTable), new Object[]{"sss", "ici", "iti", 's', "plateSteel", 'i', "ingotIron", 'c', "cpu", 't', Blocks.crafting_table});
		Register.addShapedRecipie(new ItemStack(bluePrint, 1, 0), new Object[]{" l ", "lbl", " l ", 'b', "testingWall", 'l', new ItemStack(Items.dye, 1, 4)});
		Register.addShapedRecipie(new ItemStack(bluePrint, 1, 1), new Object[]{" d ", " b ", "   ", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)});
		Register.addShapedRecipie(new ItemStack(bluePrint, 1, 2), new Object[]{"  d", " b ", "   ", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)});
		Register.addShapedRecipie(new ItemStack(bluePrint, 1, 3), new Object[]{"   ", " bd", "   ", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)});
		Register.addShapedRecipie(new ItemStack(bluePrint, 1, 4), new Object[]{"   ", " b ", "  d", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)});
		Register.addShapedRecipie(new ItemStack(bluePrint, 1, 0), new Object[]{"d  ", " b ", "   ", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)});

		Register.addShapedRecipie(new ItemStack(poweredChest), new Object[]{"c", "r", 'c', Blocks.chest, 'r', Items.redstone});
	}
}
