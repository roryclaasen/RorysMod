package net.roryclaasen.rorysmod.data;

import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.roryclaasen.rorysmod.item.ItemBase;
import net.roryclaasen.rorysmod.item.ItemDust;
import net.roryclaasen.rorysmod.item.ItemIngot;
import net.roryclaasen.rorysmod.item.ItemPlate;
import net.roryclaasen.rorysmod.item.ItemRifle;
import net.roryclaasen.rorysmod.item.ItemRifleUpgrade;
import net.roryclaasen.rorysmod.util.ItemRegistry;
import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems implements TypeGroup {

	public ItemRegistry registry = new ItemRegistry();

	public static Item steelIngot, steelDust, steelPlate;
	public static Item rifle, laserBolt;
	public static Item rifleUpgrade;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		steelIngot = new ItemIngot("ingotSteel");
		steelDust = new ItemDust("dustSteel");
		steelPlate = new ItemPlate("plateSteel");
		rifle = new ItemRifle("rifle");
		laserBolt = new ItemBase("laser");
		rifleUpgrade = new ItemRifleUpgrade("rifleUpgrade");
	}

	@Override
	public void register(FMLInitializationEvent event) {
		RMLog.info("Registering Items");
		
		GameRegistry.registerItem(steelIngot, steelIngot.getUnlocalizedName());
		GameRegistry.registerItem(steelDust, steelDust.getUnlocalizedName());
		GameRegistry.registerItem(steelPlate, steelPlate.getUnlocalizedName());
		GameRegistry.registerItem(rifle, rifle.getUnlocalizedName());
		GameRegistry.registerItem(laserBolt, laserBolt.getUnlocalizedName());
		GameRegistry.registerItem(rifleUpgrade, rifleUpgrade.getUnlocalizedName());

		OreDictionary.registerOre("ingotSteel", steelIngot);
		OreDictionary.registerOre("dustSteel", steelDust);
		OreDictionary.registerOre("plateSteel", steelPlate);
	}

	@Override
	public void createRecipes() {
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(steelDust), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), "dustIron"));
		GameRegistry.addSmelting(new ItemStack(steelDust), new ItemStack(steelIngot), 0.1f);
		Recipes.metalformerRolling.addRecipe(new RecipeInputItemStack(new ItemStack(steelIngot)), null, new ItemStack(steelPlate));
		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(steelPlate)), null, new ItemStack(steelDust));
	}
}
