package net.roryclaasen.rorysmod.data;

import net.minecraft.item.Item;
import net.roryclaasen.rorysmod.item.ItemRifle;
import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class RorysModItems implements TypeGroup {

	public static Item rifle;

	@Override
	public void init(FMLInitializationEvent event) {
		RMLog.info("Registering Items");
		rifle = new ItemRifle("laserRifle");
		
		GameRegistry.registerItem(rifle, rifle.getUnlocalizedName());
	}

	@Override
	public void createRecipes() {}
}
