package net.roryclaasen.rorysmod.item;

import ic2.api.item.ElectricItem;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRifle extends ItemBaseElectric {

	public ItemRifle(String unlocalizedName) {
		super(unlocalizedName, 0, 0, 0, 0);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setFull3D();
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.stackTagCompound = new NBTTagCompound();
		itemStack.stackTagCompound.setDouble("maxCharge", 0.0);
		itemStack.stackTagCompound.setDouble("transferLimit", 0.0);
		itemStack.stackTagCompound.setDouble("usage", 0.0);
		itemStack.stackTagCompound.setInteger("tier", 0);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		updateNBT(itemStack.stackTagCompound);
		if (player.capabilities.isCreativeMode) fireRifle(itemStack, world, player);
		else if (ElectricItem.manager.use(itemStack, usage, player)) fireRifle(itemStack, world, player);
		return itemStack;
	}

	public void updateNBT(NBTTagCompound nbtTagCompound) {
		this.maxCharge = nbtTagCompound.getInteger("maxCharge");
		this.transferLimit = nbtTagCompound.getInteger("transferLimit");
		this.usage = nbtTagCompound.getInteger("usage");
		this.tier = nbtTagCompound.getInteger("tier");
	}

	public void fireRifle(ItemStack itemStack, World world, EntityPlayer player) {
		// player.swingItem();
		world.playSoundAtEntity(player, RorysMod.MODID + ":laser_gun", 0.5F, 1.0F);
		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityLaser(world, player, itemStack.stackTagCompound));
		}
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		NBTTagCompound nbtTagCompound = stack.getTagCompound();
		tooltip.add("Laser yeah");
	}
}
