package net.roryclaasen.rorysmod.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.core.Settings;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.util.NBTLaser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRifle extends ItemBaseEnergyContainer {

	public ItemRifle(String unlocalizedName, int tier) {
		super(unlocalizedName, 1000, 10);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
		this.setFull3D();
		this.setTier(tier);
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		updateNBT(itemStack);
		updateItemDamage(itemStack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!NBTLaser.hasKeys(itemStack.stackTagCompound)) onCreated(itemStack, world, player);
		updateNBT(itemStack);

		if (!world.isRemote) {
			if (player.capabilities.isCreativeMode) fireRifle(itemStack, world, player);
			else {
				NBTLaser data = new NBTLaser(itemStack.stackTagCompound);
				if (data.checkWeight(this.tier)) {
					if (!data.overheating()) {
						if (this.use(itemStack, false)) {
							fireRifle(itemStack, world, player);
							data.setCooldown(data.getMaxCooldown());
							updateItemDamage(itemStack);
							itemStack.stackTagCompound = data.getTag();
						}
					} else {
						// TODO Overheating
					}
				} else {
					player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal(RorysMod.MODID + "_rifle.state.jammed.message")));
					world.playSoundAtEntity(player, RorysMod.MODID + ":laser_gun_fail", 0.5F, 1.0F);
				}
			}
		}
		return itemStack;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		NBTLaser data = new NBTLaser(itemstack.stackTagCompound);
		if (data.getCooldown() > 0) {
			data.setCooldown(data.getCooldown() - 1);
		}
		itemstack.stackTagCompound = data.getTag();
	}

	public void updateNBT(ItemStack itemStack) {
		NBTLaser data = new NBTLaser(itemStack.stackTagCompound);
		this.maxReceive = 10 * this.tier;

		this.capacity = (int) Math.ceil(1000 + (1000 * (data.getItemCount(NBTLaser.Items.Capacitor)) + (((double) data.getItemCount(NBTLaser.Items.Overclock)) * 5)));

		this.maxExtract = 10 + (111 * data.getItemCount(NBTLaser.Items.Overclock)) + (75 * data.getItemCount(NBTLaser.Items.Capacitor)) - (80 * data.getItemCount(NBTLaser.Items.Coolant));
		if (data.getItemCount(NBTLaser.Items.Explosion) > 0) this.maxExtract += 100 * data.getItemCount(NBTLaser.Items.Explosion);
		if (data.getItemCount(NBTLaser.Items.Phaser) > 0) this.maxExtract += 100 * data.getItemCount(NBTLaser.Items.Phaser);

		itemStack.stackTagCompound = data.getTag();
	}

	private void fireRifle(ItemStack itemStack, World world, EntityPlayer player) {
		world.playSoundAtEntity(player, RorysMod.MODID + ":laser_gun", 0.5F, 1.0F);
		world.spawnEntityInWorld(new EntityLaser(world, player, itemStack));
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		NBTLaser data = new NBTLaser(stack.stackTagCompound);
		if (Settings.laserTooltip) {
			// tooltip.add("Tier " + this.tier);
			if (data != null) {
				if (data.getCooldown() == 0) {
					int capacitor = data.getItemCount(NBTLaser.Items.Capacitor);
					int coolant = data.getItemCount(NBTLaser.Items.Coolant);
					int phaser = data.getItemCount(NBTLaser.Items.Phaser);
					int overclock = data.getItemCount(NBTLaser.Items.Overclock);
					int explosion = data.getItemCount(NBTLaser.Items.Explosion);
					if (capacitor > 0) tooltip.add(capacitor + " Capacitor(s)");
					if (coolant > 0) tooltip.add(coolant + " Coolant(s)");
					if (data.hasLens()) tooltip.add("RGB: " + data.getColor().getRed() + "," + data.getColor().getGreen() + "," + data.getColor().getBlue());
					if (overclock > 0) tooltip.add(overclock + " Overclock(s)");
					if (explosion > 0) tooltip.add(explosion + " Explosion(s)");
					if (phaser > 0) tooltip.add(phaser + " Phaser(s)");
				} else tooltip.add("Heat " + data.getCooldown());
				tooltip.add("Weight " + data.getWeight() + "/" + NBTLaser.getMaxWeight(this.tier));

				if (stack.stackTagCompound.hasKey("Energy")) {
					int energy = stack.stackTagCompound.getInteger("Energy");
					tooltip.add(energy + "/" + this.capacity + "RF");
				}
			}
		}
	}
}
