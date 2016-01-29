package net.roryclaasen.rorysmod.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface TypeGroup {

	public void preInit(FMLPreInitializationEvent event);
	
	public void register(FMLPreInitializationEvent event);

	public void createRecipes();
}
