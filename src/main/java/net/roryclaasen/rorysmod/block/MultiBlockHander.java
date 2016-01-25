package net.roryclaasen.rorysmod.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public abstract class MultiBlockHander extends ItemBlockWithMetadata {

	public MultiBlockHander(Block block) {
		super(block, block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return this.getUnlocalizedName() + "_" + itemstack.getItemDamage();
	}
}
