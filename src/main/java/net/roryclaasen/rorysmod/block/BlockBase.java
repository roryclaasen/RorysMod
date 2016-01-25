package net.roryclaasen.rorysmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.roryclaasen.rorysmod.RorysMod;

public class BlockBase extends Block {

	public BlockBase(Material material, String unlocalizedName) {
		super(material);
		this.setBlockName(RorysMod.MODID + "_" + unlocalizedName);
		this.setBlockTextureName(RorysMod.MODID + ":" + unlocalizedName);
		this.setCreativeTab(RorysMod.tab);
	}
}
