package net.roryclaasen.rorysmod.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.roryclaasen.rorysmod.core.ModItems;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;
import net.roryclaasen.rorysmod.render.RenderLaser;
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
		
		MinecraftForgeClient.registerItemRenderer(ModItems.rifle, new RenderRifle());
		
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRifleTable.class, new RenderRifleTable());
	}
}
