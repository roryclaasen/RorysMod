package net.roryclaasen.rorysmod.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.roryclaasen.rorysmod.data.RorysModItems;
import net.roryclaasen.rorysmod.entity.EntityLaser;
import net.roryclaasen.rorysmod.render.RenderLaserBolt;
import net.roryclaasen.rorysmod.render.RenderLaserRifle;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, new RenderLaserBolt());
		MinecraftForgeClient.registerItemRenderer(RorysModItems.rifle, new RenderLaserRifle());
	}
}
