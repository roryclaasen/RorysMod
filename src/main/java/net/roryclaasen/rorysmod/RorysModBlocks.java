package net.roryclaasen.rorysmod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.roryclaasen.rorysmod.block.BlockTestingWall;
import net.roryclaasen.rorysmod.util.BlockRegistry;
import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class RorysModBlocks {

	public BlockRegistry registry = new BlockRegistry();

	public Block testingWall;

	public void init(FMLInitializationEvent event) {
		RMLog.info("Registering Blocks");

		testingWall = new BlockTestingWall(Material.iron, Settings.idBlockTestingWall, "blockTest");

		GameRegistry.registerBlock(testingWall, /* MultiBlockHander.class, */"blockTest");
	}
}
