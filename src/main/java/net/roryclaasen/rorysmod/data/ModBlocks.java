package net.roryclaasen.rorysmod.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.roryclaasen.rorysmod.block.BlockTestingWall;
import net.roryclaasen.rorysmod.block.MultiBlockHandler;
import net.roryclaasen.rorysmod.util.BlockRegistry;
import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks implements TypeGroup {

	public BlockRegistry registry = new BlockRegistry();

	public Block testingWall;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RMLog.info("Registering Blocks");

		testingWall = new BlockTestingWall(Material.iron, "blockTest");

	}

	@Override
	public void register(FMLInitializationEvent event) {
		GameRegistry.registerBlock(testingWall, MultiBlockHandler.class, testingWall.getUnlocalizedName());
	}

	@Override
	public void createRecipes() {
		for (int id = 0; id < ((BlockTestingWall) testingWall).getMaxMeta(); id++) {
			ItemStack dye = new ItemStack(Items.dye, 1, 15 - id);
			ItemStack currentBlock = new ItemStack(testingWall, 1, id);
			/* if (15 - id != 15) */GameRegistry.addShapedRecipe(currentBlock, new Object[]{" i ", "idi", " i ", 'i', Items.iron_ingot, 'd', dye});
		}
	}
}
