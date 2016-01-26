package net.roryclaasen.rorysmod.data;

import net.minecraft.item.Item;
import net.roryclaasen.rorysmod.item.ItemBase;
import net.roryclaasen.rorysmod.item.ItemRifle;
import net.roryclaasen.rorysmod.item.ItemRifleUpgrade;
import net.roryclaasen.rorysmod.util.ItemRegistry;
import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems implements TypeGroup {
	public ItemRegistry registry = new ItemRegistry();

	public static Item rifle, laserBolt;
	public static Item rifleUpgrade;

	@Override
	public void init(FMLInitializationEvent event) {
		RMLog.info("Registering Items");
		rifle = new ItemRifle("rifle");
		laserBolt = new ItemBase("laser");
		rifleUpgrade = new ItemRifleUpgrade("rifleUpgrade");
		
		GameRegistry.registerItem(rifle, rifle.getUnlocalizedName());
		GameRegistry.registerItem(laserBolt, laserBolt.getUnlocalizedName());
		GameRegistry.registerItem(rifleUpgrade, rifleUpgrade.getUnlocalizedName());
	}

	@Override
	public void createRecipes() {}
}
