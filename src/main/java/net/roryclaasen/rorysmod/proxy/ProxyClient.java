package net.roryclaasen.rorysmod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ProxyClient extends ProxyCommon {

	@Override
	public void registerRenderThings() {}

	public void init(FMLInitializationEvent event) {

	}

	public EntityPlayer getPlayer() {
		return FMLClientHandler.instance().getClientPlayerEntity();
	}
}
