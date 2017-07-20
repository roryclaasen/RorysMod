/*
 * Copyright 2016-2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmod.core.network.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.intergrations.WailaConfig;
import me.roryclaasen.rorysmod.core.register.Register;
import me.roryclaasen.rorysmod.entity.EntityLaser;
import me.roryclaasen.rorysmod.entity.tile.TileEntityPoweredChest;
import me.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;
import me.roryclaasen.rorysmod.render.ItemRender;
import me.roryclaasen.rorysmod.render.RenderLaser;
import me.roryclaasen.rorysmod.render.RenderPoweredChest;
import me.roryclaasen.rorysmod.render.RenderRifle;
import me.roryclaasen.rorysmod.render.RenderRifleTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		Register.registerGlobalEntityID(EntityLaser.class, "laser");

		Register.registerEntityRenderingHandler(EntityLaser.class, new RenderLaser());

		Register.registerItemRenderer(RorysMod.items.rifle1, new RenderRifle());
		Register.registerItemRenderer(RorysMod.items.rifle2, new RenderRifle());
		Register.registerItemRenderer(RorysMod.items.rifle3, new RenderRifle());
		Register.registerItemRenderer(RorysMod.items.rifle4, new RenderRifle());
		Register.registerItemRenderer(RorysMod.items.rifle5, new RenderRifle());

		TileEntitySpecialRenderer render = new RenderRifleTable();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRifleTable.class, render);
		Register.registerItemRenderer(Item.getItemFromBlock(RorysMod.blocks.upgradeTable), new ItemRender(render, new TileEntityRifleTable()));

		render = new RenderPoweredChest();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredChest.class, render);

		FMLInterModComms.sendMessage("Waila", "register", WailaConfig.class.getName() + ".callbackRegister");
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
}
