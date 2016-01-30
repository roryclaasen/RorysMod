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
		this.maxCharge = 100;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		checkNbt(itemStack);
		updateLaserData(itemStack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!LaserData.hasAllKeys(itemStack.stackTagCompound)) onCreated(itemStack, world, player);
		updateNBT(itemStack);
		if (!world.isRemote) {
			if (player.capabilities.isCreativeMode) fireRifle(itemStack, world, player);
			else {
				LaserData data = new LaserData(itemStack.stackTagCompound);
				if (data.canFire()) {
					if (ElectricItem.manager.use(itemStack, usage, player)) {
						fireRifle(itemStack, world, player);
					}
				} else {
					// TODO Sound broken/jammed
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
		world.spawnEntityInWorld(new EntityLaser(world, player, itemStack));
	}

	private void checkNbt(ItemStack stack) {
		if (stack.stackTagCompound == null) stack.stackTagCompound = LaserData.getNewStackTagCompound();
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if (!LaserData.hasAllKeys(stack.stackTagCompound)) onCreated(stack, playerIn.worldObj, playerIn);
		if (Settings.laserTooltip) {
			LaserData data = new LaserData(stack.stackTagCompound);
			if (data != null) {
				tooltip.add("Tier " + (data.getTier() == 0 ? 1 : data.getTier()));
				if (data.getCapacitor() > 0) tooltip.add(data.getCapacitor() + " Capacitor(s)");
				if (data.getCoolant() > 0) tooltip.add(data.getCoolant() + " Coolant(s)");
				if (data.getLens() > 0) tooltip.add(data.getLens() + " Lens(s)");
				if (data.getOverclock() > 0) tooltip.add(data.getOverclock() + " Overclock(s)");
				if (data.getExplosion() > 0) tooltip.add(data.getExplosion() + " Explosion(s)");
				if (data.getPhaser() > 0) tooltip.add(data.getPhaser() + " Phaser(s)");
			}
		}
	}
}
