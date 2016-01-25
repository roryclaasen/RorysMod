package net.roryclaasen.rorysmod.data;

import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class RorysModItems extends TypeGroup {

	@Override
	public void init(FMLInitializationEvent event) {
		RMLog.info("Registering Items");
	}

	@Override
	public void createRecipes() {}
}
