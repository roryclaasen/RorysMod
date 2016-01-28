package net.roryclaasen.rorysmod.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.RorysMod;

public class BlockBaseContainer extends BlockContainer {

	protected BlockBaseContainer(Material material, String unlocalizedName) {
		super(material);
		this.setBlockName(RorysMod.MODID + "_" + unlocalizedName);
		this.setBlockTextureName(RorysMod.MODID + ":" + unlocalizedName);
		this.setCreativeTab(RorysMod.tab);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

}
