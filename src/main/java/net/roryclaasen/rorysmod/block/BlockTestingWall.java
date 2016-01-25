package net.roryclaasen.rorysmod.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTestingWall extends BlockBase {

	public IIcon[] icons = new IIcon[16];

	public BlockTestingWall(Material material, int id, String unlocalizedName) {
		super(material, id, unlocalizedName);
		this.setStepSound(soundTypeMetal);
		this.setResistance(15.0F);
		this.setHardness(12.0F);
		this.setHarvestLevel("pickaxe", 2);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		for (int i = 0; i < icons.length; i++) {
			icons[i] = reg.registerIcon(this.textureName + "_" + i);
		}
	}

	@Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta > icons.length) meta = 0;
		return icons[meta];
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
    @SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < icons.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}
}
