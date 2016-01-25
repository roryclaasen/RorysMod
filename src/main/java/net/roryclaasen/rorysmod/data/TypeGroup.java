package net.roryclaasen.rorysmod.data;

import cpw.mods.fml.common.event.FMLInitializationEvent;


public abstract class TypeGroup {

	public abstract void init(FMLInitializationEvent event);
	
	public abstract void createRecipes();
}
