package net.roryclaasen.rorysmod.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.roryclaasen.rorysmod.block.BlockIngot;
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

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		testingWall = new BlockTestingWall(Material.iron, "blockTest");
		upgradeTable = new BlockRifleTable(Material.anvil, "tableUpgrade");
		steelBlock = new BlockIngot(Material.iron, "blockSteel");
		bluePrint = new BlockBlueprint(Material.iron, "blockBluePrint");
	}

	@Override
	public void register(FMLPreInitializationEvent event) {
		RMLog.info("Registering Blocks");
		GameRegistry.registerBlock(testingWall, MultiBlockHandler.class, testingWall.getUnlocalizedName());
		GameRegistry.registerBlock(upgradeTable, upgradeTable.getUnlocalizedName());
		GameRegistry.registerBlock(steelBlock, steelBlock.getUnlocalizedName());
		GameRegistry.registerBlock(bluePrint, MultiBlockHandler.class, bluePrint.getUnlocalizedName());

		OreDictionary.registerOre("blockSteel", steelBlock);
	}

	@Override
	public void createRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steelBlock), new Object[]{"sss", "sss", "sss", 's', "ingotSteel"}));
		for (int id = 0; id < ((BlockTestingWall) testingWall).getMaxMeta(); id++) {
			ItemStack dye = new ItemStack(Items.dye, 1, 15 - id);
			ItemStack currentBlock = new ItemStack(testingWall, 1, id);
			/* if (15 - id != 15) */GameRegistry.addRecipe(new ShapedOreRecipe(currentBlock, new Object[]{" i ", "idi", " i ", 'i', "ingotSteel", 'd', dye}));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgradeTable), new Object[]{"sss", "ici", "iti", 's', "plateSteel", 'i', "ingotIron", 'c', "cpu", 't', Blocks.crafting_table}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(bluePrint), new Object[]{new ItemStack(testingWall, 1, 4)}));
	}
}
