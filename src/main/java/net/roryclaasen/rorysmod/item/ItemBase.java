package net.roryclaasen.rorysmod.item;

import net.minecraft.item.Item;
import net.roryclaasen.rorysmod.RorysMod;

public class ItemBase extends Item {

	public ItemBase(String unlocalizedName) {
		super();
		this.setUnlocalizedName(RorysMod.MODID + "_" + unlocalizedName);
		this.setTextureName(RorysMod.MODID + ":" + unlocalizedName);
		this.setCreativeTab(RorysMod.tab);
	}
}
