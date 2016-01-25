package net.roryclaasen.rorysmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.data.RorysModItems;
import net.roryclaasen.rorysmod.entity.EntityLaser;

public class ItemRifle extends ItemBase {

	public ItemRifle(String unlocalizedName) {
		super(unlocalizedName);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setFull3D();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(RorysModItems.laserBolt)) {
			// player.swingItem();
			world.playSoundAtEntity(player, RorysMod.MODID + ":laser_gun", 0.5F, 1.0F);
			if (!world.isRemote) {
				world.spawnEntityInWorld(new EntityLaser(world, player));
			}
		}
		return itemStack;
	}
}
