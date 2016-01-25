package net.roryclaasen.rorysmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.roryclaasen.rorysmod.RorysMod;

public class BlockBase extends Block {

	private final int id;

	public BlockBase(Material material, int id, String unlocalizedName) {
		super(material);
		this.id = id;
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(RorysMod.MODID + ":" + unlocalizedName);
		this.setCreativeTab(RorysMod.tab);
	}

	public int getId() {
		return id;
	}
}
