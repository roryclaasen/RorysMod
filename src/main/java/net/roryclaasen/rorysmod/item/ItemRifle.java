package net.roryclaasen.rorysmod.item;

import ic2.api.item.ElectricItem;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.data.Settings;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.util.LaserData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRifle extends ItemBaseElectric {

	private LaserData data;

	public ItemRifle(String unlocalizedName) {
		super(unlocalizedName, 0, 0, 0, 0);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setFull3D();
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		data = new LaserData();
		itemStack.stackTagCompound = data.getNBT();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		updateNBT(itemStack);
		if (player.capabilities.isCreativeMode) fireRifle(itemStack, world, player);
		else if (ElectricItem.manager.use(itemStack, usage, player)) fireRifle(itemStack, world, player);
		return itemStack;
	}

	public void updateNBT(ItemStack itemStack) {
		itemStack.stackTagCompound = data.getNBT();
		this.tier = data.getTier();
	}

	public void fireRifle(ItemStack itemStack, World world, EntityPlayer player) {
		// player.swingItem();
		world.playSoundAtEntity(player, RorysMod.MODID + ":laser_gun", 0.5F, 1.0F);
		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityLaser(world, player, data));
		}
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if (Settings.laserTooltip) {
			tooltip.add("Tier " + data.getTier());
			if (data.getCapacitor() >= 1) tooltip.add(data.getCapacitor() + " Capacitor(s)");
			if (data.getCoolant() >= 1) tooltip.add(data.getCoolant() + " Coolant(s)");
			if (data.getLens() >= 1) tooltip.add(data.getLens() + " Lens(s)");
			if (data.getOverclock() >= 1) tooltip.add(data.getOverclock() + " Overclock(s)");
			if (data.getExplosion() >= 1) tooltip.add(data.getExplosion() + " Explosion(s)");
			if (data.getPhaser() >= 1) tooltip.add(data.getPhaser() + " Phaser(s)");
		}
	}
}
