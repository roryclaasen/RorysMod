package net.roryclaasen.rorysmod.item;

import ic2.api.item.ElectricItem;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.core.Settings;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.util.NBTLaser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRifle extends ItemBaseElectric {

	public ItemRifle(String unlocalizedName) {
		super(unlocalizedName, 0, 0, 0, 0);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setFull3D();
		this.maxCharge = 100;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		updateLaserData(itemStack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!NBTLaser.hasKeys(itemStack.stackTagCompound)) onCreated(itemStack, world, player);
		updateNBT(itemStack);
		if (!world.isRemote) {
			if (player.capabilities.isCreativeMode) fireRifle(itemStack, world, player);
			else {
				NBTLaser data = new NBTLaser(itemStack.stackTagCompound);
				if (data.canFire(this.tier)) {
					if (ElectricItem.manager.use(itemStack, usage, player)) {
						fireRifle(itemStack, world, player);
					}
				} else {
					player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal(RorysMod.MODID + "_rifle.state.jammed.message")));
					world.playSoundAtEntity(player, RorysMod.MODID + ":laser_gun_fail", 0.5F, 1.0F);
				}
			}
		}
		return itemStack;
	}

	public void updateNBT(ItemStack itemStack) {
		updateLaserData(itemStack);

		double currentCharge = ElectricItem.manager.getCharge(itemStack);
		if (currentCharge >= this.maxCharge) ElectricItem.manager.discharge(itemStack, this.maxCharge - currentCharge, this.tier, true, false, false);
	}

	public void updateLaserData(ItemStack itemStack) {
		checkNbt(itemStack);
		NBTLaser data = new NBTLaser(itemStack.stackTagCompound);
		if (data != null) {
			this.transferLimit = (10 * this.tier) + (1.75 * data.getItemCount(NBTLaser.Items.Capacitor)) + (1.25 * data.getItemCount(NBTLaser.Items.Coolant)) - (1.1 * data.getItemCount(NBTLaser.Items.Overclock));

			this.maxCharge = 100 + 200 * (data.getItemCount(NBTLaser.Items.Capacitor) + (((double) data.getItemCount(NBTLaser.Items.Overclock)) / 0.5));

			this.usage = 10 + (11.75 * data.getItemCount(NBTLaser.Items.Overclock)) + (4.25 * data.getItemCount(NBTLaser.Items.Capacitor)) - (3.5 * data.getItemCount(NBTLaser.Items.Coolant));
			if (data.getItemCount(NBTLaser.Items.Explosion) > 0) this.usage += 20 * data.getItemCount(NBTLaser.Items.Explosion);
			if (data.getItemCount(NBTLaser.Items.Phaser) > 0) this.usage += 20 * data.getItemCount(NBTLaser.Items.Phaser);
		}
	}

	public void fireRifle(ItemStack itemStack, World world, EntityPlayer player) {
		// player.swingItem();
		world.playSoundAtEntity(player, RorysMod.MODID + ":laser_gun", 0.5F, 1.0F);
		world.spawnEntityInWorld(new EntityLaser(world, player, itemStack));
	}

	private void checkNbt(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			NBTLaser laser = new NBTLaser(new NBTTagCompound());
			laser.setUp();
			stack.stackTagCompound = laser.getTag();
		}
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if (!NBTLaser.hasKeys(stack.stackTagCompound)) onCreated(stack, playerIn.worldObj, playerIn);
		if (Settings.laserTooltip) {
			NBTLaser data = new NBTLaser(stack.stackTagCompound);
			if (data != null) {
				tooltip.add("Tier " + this.tier);
				int capacitor = data.getItemCount(NBTLaser.Items.Capacitor);
				int coolant = data.getItemCount(NBTLaser.Items.Coolant);
				int lens = data.getItemCount(NBTLaser.Items.Lens);
				int phaser = data.getItemCount(NBTLaser.Items.Phaser);
				int overclock = data.getItemCount(NBTLaser.Items.Overclock);
				int explosion = data.getItemCount(NBTLaser.Items.Explosion);
				if (capacitor > 0) tooltip.add(capacitor + " Capacitor(s)");
				if (coolant > 0) tooltip.add(coolant + " Coolant(s)");
				if (lens > 0) tooltip.add("RGB: " + data.getColor().getRed() + "," + data.getColor().getGreen() + "," + data.getColor().getBlue());
				if (overclock> 0) tooltip.add(overclock + " Overclock(s)");
				if (explosion > 0) tooltip.add(explosion + " Explosion(s)");
				if (phaser > 0) tooltip.add(phaser + " Phaser(s)");
				tooltip.add("Weight " + data.getWeight() + "/" + NBTLaser.getMaxWeight(this.tier));
			}
		}
	}
}
