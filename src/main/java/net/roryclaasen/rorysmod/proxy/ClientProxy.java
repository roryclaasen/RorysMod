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
package net.roryclaasen.rorysmod.proxy;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.roryclaasen.rorysmod.core.ModBlocks;
import net.roryclaasen.rorysmod.core.ModItems;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.entity.tile.TileEntityPoweredChest;
import net.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;
import net.roryclaasen.rorysmod.render.ItemRender;
import net.roryclaasen.rorysmod.render.RenderLaser;
import net.roryclaasen.rorysmod.render.RenderPoweredChest;
import net.roryclaasen.rorysmod.render.RenderRifle;
import net.roryclaasen.rorysmod.render.RenderRifleTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		EntityRegistry.registerGlobalEntityID(EntityLaser.class, "laser", EntityRegistry.findGlobalUniqueEntityId());

		RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, new RenderLaser());

		MinecraftForgeClient.registerItemRenderer(ModItems.rifle1, new RenderRifle());

		TileEntitySpecialRenderer render = new RenderRifleTable();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRifleTable.class, render);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.upgradeTable), new ItemRender(render, new TileEntityRifleTable()));

		render = new RenderPoweredChest();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredChest.class, render);
		// MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.upgradeTable), new ItemRender(render, new TileEntityRifleTable()));
	}
}
