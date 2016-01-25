package net.roryclaasen.rorysmod.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class MultiBlockHandler extends ItemBlockWithMetadata {

	public MultiBlockHandler(Block block) {
		super(block, block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return this.getUnlocalizedName() + "_" + itemstack.getItemDamage();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}
