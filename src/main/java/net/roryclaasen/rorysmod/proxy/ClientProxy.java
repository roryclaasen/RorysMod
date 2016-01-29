package net.roryclaasen.rorysmod.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.roryclaasen.rorysmod.core.ModItems;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.render.RenderLaser;
import net.roryclaasen.rorysmod.render.RenderRifle;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, new RenderLaser());
		MinecraftForgeClient.registerItemRenderer(ModItems.rifle, new RenderRifle());
	}
}
