package net.roryclaasen.rorysmod.event;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.roryclaasen.rorysmod.core.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerHoldingRifle {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void rifleHolding(RenderLivingEvent.Pre event) {
		if (!event.isCanceled() && event.entity instanceof EntityPlayer) {
			ItemStack item = event.entity.getHeldItem();

			if (item == null) return;

			RenderPlayer rp;
			if (item.getItem() == ModItems.rifle1) {
				rp = (RenderPlayer) event.renderer;
				rp.modelArmorChestplate.heldItemRight = rp.modelArmor.heldItemRight = rp.modelBipedMain.heldItemRight = 5;
			} else {
				rp = (RenderPlayer) event.renderer;
				rp.modelArmorChestplate.heldItemRight = rp.modelArmor.heldItemRight = rp.modelBipedMain.heldItemRight = 0;
			}
		}
	}
}
