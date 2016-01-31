package net.roryclaasen.rorysmod.block;

import net.minecraft.block.material.Material;

public class BlockIngot extends BlockBase {

	public BlockIngot(Material material, String unlocalizedName) {
		super(material, unlocalizedName);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundTypeMetal);
	}
}
