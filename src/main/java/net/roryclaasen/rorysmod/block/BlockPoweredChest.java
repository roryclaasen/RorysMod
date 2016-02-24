/*
Copyright 2016 Rory Claasen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package net.roryclaasen.rorysmod.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.entity.tile.TileEntityPoweredChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPoweredChest extends BlockBaseContainer implements ITileEntityProvider {

	private final Random random = new Random();

	public BlockPoweredChest(Material material, String unlocalizedName) {
		super(material, unlocalizedName);
		this.setHardness(2.5F);
		this.setStepSound(soundTypeWood);
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 22;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack) {
		byte chestFacing = 0;
		int facing = MathHelper.floor_double((entityliving.rotationYaw * 4F) / 360F + 0.5D) & 3;

		if (facing == 0) chestFacing = 2;
		if (facing == 1) chestFacing = 5;
		if (facing == 2) chestFacing = 3;
		if (facing == 3) chestFacing = 4;

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityPoweredChest) {
			TileEntityPoweredChest teic = (TileEntityPoweredChest) te;
			teic.wasPlaced(entityliving, itemStack);
			teic.setFacing(chestFacing);
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float lx, float ly, float lz) {
		if (world.isRemote) return true;
		if (world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN)) return true;

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityPoweredChest) {
			world.playSoundEffect(x, y, z, "random.chestopen", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			player.openGui(RorysMod.instance, RorysMod.GUIS.CHEST_POWERED.getId(), world, x, y, z);
			((TileEntityPoweredChest) tile).openInventory();
			return true;
		}
		return true;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
		if (world.isRemote) return;
		ArrayList drops = new ArrayList();
		TileEntity teRaw = world.getTileEntity(x, y, z);
		if (teRaw != null && teRaw instanceof TileEntityPoweredChest) {
			TileEntityPoweredChest te = (TileEntityPoweredChest) teRaw;
			for (int i = 0; i < te.getSizeInventory(); i++) {
				ItemStack stack = te.getStackInSlot(i);
				if (stack != null) drops.add(stack.copy());
			}
		}
		for (int i = 0; i < drops.size(); i++) {
			EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, (ItemStack) drops.get(i));
			item.setVelocity((random.nextDouble() - 0.5) * 0.25, random.nextDouble() * 0.5 * 0.25, (random.nextDouble() - 0.5) * 0.25);
			world.spawnEntityInWorld(item);
		}
	}

	public TileEntity createNewTileEntity(World world, int par2) {
		return new TileEntityPoweredChest();
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon("planks_oak");
	}
}
