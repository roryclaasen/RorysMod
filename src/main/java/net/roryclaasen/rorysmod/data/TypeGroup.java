package net.roryclaasen.rorysmod.data;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public interface TypeGroup {

	public void init(FMLInitializationEvent event);

	public void createRecipes();
}
