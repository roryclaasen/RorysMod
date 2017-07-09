/*
Copyright 2016-2017 Rory Claasen

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
package me.roryclaasen.rorysmod.item;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.Settings;
import me.roryclaasen.rorysmod.entity.EntityLaser;
import me.roryclaasen.rorysmod.util.ColorUtils;
import me.roryclaasen.rorysmod.util.NBTLaser;
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
							itemStack.stackTagCompound = data.getTag();
						}
					} else {
						// player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.rifle.state.overheat")));
					}
				} else {
					player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.rifle.state.jammed")));
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

		this.maxExtract = 10 + (75 * data.getItemCount(NBTLaser.Items.Overclock)) + (13 * data.getItemCount(NBTLaser.Items.Capacitor)) - data.getItemCount(NBTLaser.Items.Coolant) + (60 * data.getItemCount(NBTLaser.Items.Explosion)) + (60 * data.getItemCount(NBTLaser.Items.Phaser));
		if (this.maxExtract < 10) this.maxExtract = 10;

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
		if (!NBTLaser.hasKeys(stack.stackTagCompound)) onCreated(stack, playerIn.worldObj, playerIn);
		NBTLaser data = new NBTLaser(stack.stackTagCompound);
		if (Settings.laserTooltip) {
			// tooltip.add("Tier " + this.tier);
			if (GuiScreen.isShiftKeyDown()) {
				tooltip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("message.rorysmod.rifle.discription"));
				if (data != null) {
					if (data.getCooldown() == 0) {
						int capacitor = data.getItemCount(NBTLaser.Items.Capacitor);
						int coolant = data.getItemCount(NBTLaser.Items.Coolant);
						int phaser = data.getItemCount(NBTLaser.Items.Phaser);
						int overclock = data.getItemCount(NBTLaser.Items.Overclock);
						int explosion = data.getItemCount(NBTLaser.Items.Explosion);
						int igniter = data.getItemCount(NBTLaser.Items.Igniter);
						if (capacitor > 0) tooltip.add(capacitor + " Capacitor(s)");
						if (coolant > 0) tooltip.add(coolant + " Coolant(s)");
						if (data.hasLens() && stack.stackTagCompound.hasKey("color")) {
							tooltip.add("Color: " + ColorUtils.getIntColorFromIntArray(stack.stackTagCompound.getIntArray("color")));
						}
						if (overclock > 0) tooltip.add(overclock + " Overclock(s)");
						if (explosion > 0) tooltip.add(explosion + " Explosion(s)");
						if (phaser > 0) tooltip.add(phaser + " Phaser(s)");
						if (phaser > 0) tooltip.add(phaser + " Phaser(s)");
						if (igniter > 0) tooltip.add(igniter + " Igniter(s)");
					} else tooltip.add(StatCollector.translateToLocal("message.rorysmod.heat") + " " + data.getCooldown());
					if (data.getWeight() > NBTLaser.getMaxWeight(this.tier)) tooltip.add(EnumChatFormatting.RED + "Weight " + data.getWeight() + "/" + NBTLaser.getMaxWeight(this.tier));
					else tooltip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("message.rorysmod.weight") + " " + data.getWeight() + "/" + NBTLaser.getMaxWeight(this.tier));
				}
			} else tooltip.add(StatCollector.translateToLocal("message.rorysmod.holdShift1") + " " + EnumChatFormatting.YELLOW + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("message.rorysmod.holdShift2") + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " " + StatCollector.translateToLocal("message.rorysmod.holdShift3"));
			int energy = 0;
			if (stack.stackTagCompound.hasKey("Energy")) energy = stack.stackTagCompound.getInteger("Energy");
			tooltip.add(StatCollector.translateToLocal("message.rorysmod.charge") + ": " + energy + " / " + this.capacity + " RF");
		}
	}
}
