package me.roryclaasen.rorysmod.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import me.roryclaasen.rorysmod.block.base.BlockBaseContainer;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.entity.tile.TileEntityRenamer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMachineRenamer extends BlockBaseContainer {

	private IIcon[] icons = new IIcon[3];

	public BlockMachineRenamer(Material material, String unlocalizedName) {
		super(material, unlocalizedName);
		this.setHarvestLevel("pickaxe", 2);
		this.setStepSound(soundTypePiston);
		this.setHardness(6.0F);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) FMLNetworkHandler.openGui(player, RorysMod.instance, RorysMod.GUIS.MACHINE_RENAMER.ordinal(), world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
		super.breakBlock(world, x, y, z, block, par6);
		
		// TODO keep nbt data
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.icons[0] = reg.registerIcon(RorysMod.MODID + ":machineTop");
		this.icons[1] = reg.registerIcon(RorysMod.MODID + ":machineRenamer");
		this.icons[2] = reg.registerIcon(RorysMod.MODID + ":machineBottom");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		switch (side) {
			case (0):
				return this.icons[2];
			case (1):
				return this.icons[0];
			default:
				return this.icons[1];
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRenamer();
	}
}
