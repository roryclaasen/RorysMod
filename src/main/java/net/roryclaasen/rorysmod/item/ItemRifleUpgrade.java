package net.roryclaasen.rorysmod.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.roryclaasen.rorysmod.RorysMod;

public class ItemRifleUpgrade extends ItemBase {

	public IIcon[] icons = new IIcon[7];

	public ItemRifleUpgrade(String unlocalizedName) {
		super(unlocalizedName);
		this.setHasSubtypes(true);
		this.setMaxStackSize(16);
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		for (int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon(RorysMod.MODID + ":" + getName() + "_" + i);
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < icons.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta >= icons.length) meta = 0;
		return this.icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		tooltip.add(StatCollector.translateToLocal(getUnlocalizedName(stack) + ".tooltip"));
	}
}
