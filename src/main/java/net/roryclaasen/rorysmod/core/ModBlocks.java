/*
Copyright 2016 Rory Claasen

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
package net.roryclaasen.rorysmod.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.roryclaasen.rorysmod.block.BlockBaseMeta;
import net.roryclaasen.rorysmod.block.BlockIngot;
import net.roryclaasen.rorysmod.block.BlockPoweredChest;
import net.roryclaasen.rorysmod.block.BlockRifleTable;
import net.roryclaasen.rorysmod.block.BlockTestingWall;
import net.roryclaasen.rorysmod.block.BlockBlueprint;
import net.roryclaasen.rorysmod.block.MultiBlockHandler;
import net.roryclaasen.rorysmod.util.RMLog;
import net.roryclaasen.rorysmod.util.registry.BlockRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

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
		GameRegistry.registerBlock(testingWall, MultiBlockHandler.class, testingWall.getUnlocalizedName());
		GameRegistry.registerBlock(upgradeTable, upgradeTable.getUnlocalizedName());
		GameRegistry.registerBlock(steelBlock, steelBlock.getUnlocalizedName());
		GameRegistry.registerBlock(bluePrint, MultiBlockHandler.class, bluePrint.getUnlocalizedName());

		OreDictionary.registerOre("blockSteel", steelBlock);
		for (int i = 0; i < ((BlockBaseMeta) testingWall).getMetaSize(); i++) {
			OreDictionary.registerOre("testingWall", new ItemStack(testingWall, 1, i));
		}
		for (int i = 0; i < ((BlockBaseMeta) bluePrint).getMetaSize(); i++) {
			OreDictionary.registerOre("bluePrint", new ItemStack(bluePrint, 1, i));
		}

		GameRegistry.registerBlock(poweredChest, poweredChest.getUnlocalizedName());
	}

	@Override
	public void createRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steelBlock), new Object[]{"sss", "sss", "sss", 's', "ingotSteel"}));
		for (int id = 0; id < ((BlockBaseMeta) testingWall).getMetaSize(); id++) {
			ItemStack dye = new ItemStack(Items.dye, 1, 15 - id);
			ItemStack currentBlock = new ItemStack(testingWall, 1, id);
			GameRegistry.addRecipe(new ShapedOreRecipe(currentBlock, new Object[]{" i ", "idi", " i ", 'i', "ingotSteel", 'd', dye}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(currentBlock, new Object[]{"testingWall", dye, dye, dye}));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgradeTable), new Object[]{"sss", "ici", "iti", 's', "plateSteel", 'i', "ingotIron", 'c', "cpu", 't', Blocks.crafting_table}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bluePrint, 1, 0), new Object[]{" l ", "lbl", " l ", 'b', "testingWall", 'l', new ItemStack(Items.dye, 1, 4)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bluePrint, 1, 1), new Object[]{" d ", " b ", "   ", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bluePrint, 1, 2), new Object[]{"  d", " b ", "   ", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bluePrint, 1, 3), new Object[]{"   ", " bd", "   ", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bluePrint, 1, 4), new Object[]{"   ", " b ", "  d", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bluePrint, 1, 0), new Object[]{"d  ", " b ", "   ", 'b', "bluePrint", 'd', new ItemStack(Items.dye, 1, 15)}));

		GameRegistry.addShapedRecipe(new ItemStack(poweredChest), new Object[]{"c", "r", 'c', Blocks.chest, 'r', Items.redstone});
	}
}
