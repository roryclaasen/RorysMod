package me.roryclaasen.rorysmod.block;

import java.util.ArrayList;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import me.roryclaasen.rorysmod.block.base.BlockBaseContainer;
import me.roryclaasen.rorysmod.block.tile.TileEntityRenamer;
import me.roryclaasen.rorysmod.core.RorysGlobal;
import me.roryclaasen.rorysmod.core.RorysMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		super.onBlockPlacedBy(world, z, y, z, entity, itemStack);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityRenamer) {
			if (itemStack == null) return;
			if (itemStack.stackTagCompound == null) return;
			if (!itemStack.stackTagCompound.hasKey("blockData")) return;
			NBTTagCompound nbtCompount = itemStack.stackTagCompound.getCompoundTag("blockData");
			nbtCompount.setInteger("x", x);
			nbtCompount.setInteger("y", y);
			nbtCompount.setInteger("z", z);
			((TileEntityRenamer) tile).readFromNBT(nbtCompount);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) FMLNetworkHandler.openGui(player, RorysMod.instance, RorysMod.GUIS.MACHINE_RENAMER.ordinal(), world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
		ItemStack item = getDrops(world, x, y, z, 0, 0).get(0);
		super.breakBlock(world, x, y, z, block, par6);
		super.dropBlockAsItem(world, x, y, z, item);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof TileEntityRenamer) {
			NBTTagCompound blockTagCompound = new NBTTagCompound();
			tileEntity.writeToNBT(blockTagCompound);

			NBTTagCompound itemTagCompound = new NBTTagCompound();
			ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
			itemstack.writeToNBT(itemTagCompound);
			itemTagCompound.setTag("blockData", blockTagCompound);
			itemstack.stackTagCompound = itemTagCompound;
			ret.add(itemstack);
		}
		return ret;
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.icons[0] = reg.registerIcon(RorysGlobal.MODID + ":machineTop");
		this.icons[1] = reg.registerIcon(RorysGlobal.MODID + ":machineRenamer");
		this.icons[2] = reg.registerIcon(RorysGlobal.MODID + ":machineBottom");
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
