package net.roryclaasen.rorysmod.data;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface TypeGroup {

	public void preInit(FMLPreInitializationEvent event);
	
	public void register(FMLInitializationEvent event);

	public void createRecipes();
}
