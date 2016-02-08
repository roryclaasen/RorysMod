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
