package net.roryclaasen.rorysmod.item;

import ic2.api.item.ElectricItem;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.core.Settings;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.util.LaserData;
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
		itemStack.stackTagCompound = (new LaserData()).getNBT();
		updateLaserData(itemStack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		updateNBT(itemStack);
		if (player.capabilities.isCreativeMode) fireRifle(itemStack, world, player);
		else if (ElectricItem.manager.use(itemStack, usage, player)) fireRifle(itemStack, world, player);
		return itemStack;
	}

	public void updateNBT(ItemStack itemStack) {
		updateLaserData(itemStack);

		double currentCharge = ElectricItem.manager.getCharge(itemStack);
		if (currentCharge >= this.maxCharge) ElectricItem.manager.discharge(itemStack, this.maxCharge - currentCharge, this.tier, true, false, false);
	}

	public void updateLaserData(ItemStack itemStack) {
		LaserData data = new LaserData(itemStack.stackTagCompound);
		if (data != null) {
			this.tier = data.getTier();

			this.transferLimit = (10 * data.getTier()) + (1.75 * data.getCapacitor()) + (1.25 * data.getCoolant()) - (1.1 * data.getOverclock());

			this.maxCharge = 100 + 200 * (data.getCapacitor() + (((double) data.getOverclock()) / 0.5));

			this.usage = 10 + (11.75 * data.getOverclock()) + (4.25 * data.getCapacitor()) - (3.5 * data.getCoolant());
			if (data.getExplosion() > 0 || data.getPhaser() > 0) this.usage += 20;
		}
	}

	public void fireRifle(ItemStack itemStack, World world, EntityPlayer player) {
		// player.swingItem();
		world.playSoundAtEntity(player, RorysMod.MODID + ":laser_gun", 0.5F, 1.0F);
		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityLaser(world, player, new LaserData(itemStack.stackTagCompound)));
		}
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if (Settings.laserTooltip) {
			LaserData data = new LaserData(stack.stackTagCompound);
			if (data != null) {
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
}
