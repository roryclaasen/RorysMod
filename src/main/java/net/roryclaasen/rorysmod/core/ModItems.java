package net.roryclaasen.rorysmod.core;

import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.roryclaasen.rorysmod.item.ItemBase;
import net.roryclaasen.rorysmod.item.ItemDust;
import net.roryclaasen.rorysmod.item.ItemIngot;
import net.roryclaasen.rorysmod.item.ItemPlate;
import net.roryclaasen.rorysmod.item.ItemRifle;
import net.roryclaasen.rorysmod.item.ItemRifleUpgrade;
import net.roryclaasen.rorysmod.util.RMLog;
import net.roryclaasen.rorysmod.util.registry.ItemRegistry;
import codechicken.nei.api.API;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems implements TypeGroup {

	public ItemRegistry registry = new ItemRegistry();

	public static Item steelIngot, steelDust, steelPlate;
	public static Item carbonIngot;
	public static Item rifle1, rifle2, rifle3, rifle4, rifle5;
	public static Item laserBolt, rifleBarrel, rifleTrigger;
	public static Item rifleUpgrade, upgradePlate;
	public static Item circuit, advancedCircuit;
	public static Item lens, filament, cpu;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		steelIngot = new ItemIngot("ingotSteel");
		steelDust = new ItemDust("dustSteel");
		steelPlate = new ItemPlate("plateSteel");
		carbonIngot = new ItemIngot("ingotCarbon");
		rifle1 = new ItemRifle("rifle1", 1);
		rifle2 = new ItemRifle("rifle2", 2);
		rifle3 = new ItemRifle("rifle3", 3);
		rifle4 = new ItemRifle("rifle4", 4);
		rifle5 = new ItemRifle("rifle5", 5);
		rifleBarrel = new ItemBase("rifleBarrel");
		rifleTrigger = new ItemBase("rifleTrigger");
		laserBolt = new ItemBase("laser").setCreativeTab(null);
		rifleUpgrade = new ItemRifleUpgrade("rifleUpgrade");
		upgradePlate = new ItemPlate("plateUpgrade");
		circuit = new ItemBase("circuit");
		advancedCircuit = new ItemBase("advancedCircuit");
		lens = new ItemBase("lens");
		filament = new ItemBase("filament");
		cpu = new ItemBase("cpu");
	}

	@Override
	public void register(FMLPreInitializationEvent event) {
		RMLog.info("Registering Items");

		GameRegistry.registerItem(steelIngot, steelIngot.getUnlocalizedName());
		GameRegistry.registerItem(steelDust, steelDust.getUnlocalizedName());
		GameRegistry.registerItem(steelPlate, steelPlate.getUnlocalizedName());
		GameRegistry.registerItem(carbonIngot, carbonIngot.getUnlocalizedName());
		GameRegistry.registerItem(rifle1, rifle1.getUnlocalizedName());
		GameRegistry.registerItem(rifle2, rifle2.getUnlocalizedName());
		GameRegistry.registerItem(rifle3, rifle3.getUnlocalizedName());
		GameRegistry.registerItem(rifle4, rifle4.getUnlocalizedName());
		GameRegistry.registerItem(rifle5, rifle5.getUnlocalizedName());
		GameRegistry.registerItem(rifleBarrel, rifleBarrel.getUnlocalizedName());
		GameRegistry.registerItem(rifleTrigger, rifleTrigger.getUnlocalizedName());
		GameRegistry.registerItem(laserBolt, laserBolt.getUnlocalizedName());
		GameRegistry.registerItem(rifleUpgrade, rifleUpgrade.getUnlocalizedName());
		GameRegistry.registerItem(upgradePlate, upgradePlate.getUnlocalizedName());
		GameRegistry.registerItem(circuit, circuit.getUnlocalizedName());
		GameRegistry.registerItem(advancedCircuit, advancedCircuit.getUnlocalizedName());
		GameRegistry.registerItem(lens, lens.getUnlocalizedName());
		GameRegistry.registerItem(filament, filament.getUnlocalizedName());
		GameRegistry.registerItem(cpu, cpu.getUnlocalizedName());

		OreDictionary.registerOre("ingotSteel", steelIngot);
		OreDictionary.registerOre("dustSteel", steelDust);
		OreDictionary.registerOre("plateSteel", steelPlate);
		OreDictionary.registerOre("ingotCarbon", carbonIngot);
		OreDictionary.registerOre("circuitBasic", circuit);
		OreDictionary.registerOre("circuitAdvanced", advancedCircuit);
		OreDictionary.registerOre("lens", lens);
		OreDictionary.registerOre("filament", filament);
		OreDictionary.registerOre("cpu", cpu);

		if (Loader.isModLoaded("NotEnoughItems")) {
			API.hideItem(new ItemStack(laserBolt));
		}
	}

	@Override
	public void createRecipes() {
		ItemStack forgeHammer = IC2Items.getItem("ForgeHammer").copy();
		forgeHammer.setItemDamage(OreDictionary.WILDCARD_VALUE);

		// Carbon
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(carbonIngot), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), "ingotIron"));
		GameRegistry.addSmelting(new ItemStack(carbonIngot), new ItemStack(steelIngot), 0.1f);

		// Steel
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(steelDust, 2), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), "dustIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(steelPlate), "ingotSteel", forgeHammer));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(steelIngot, 9), "blockSteel"));
		GameRegistry.addSmelting(new ItemStack(steelDust), new ItemStack(steelIngot), 0.1f);
		Recipes.metalformerRolling.addRecipe(new RecipeInputItemStack(new ItemStack(steelIngot)), null, new ItemStack(steelPlate));
		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(steelPlate)), null, new ItemStack(steelDust));

		// Circuit
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(circuit), new Object[]{" r ", "gsg", " r ", 'r', Items.redstone, 's', "plateSteel", 'g', Items.gold_nugget}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedCircuit), new Object[]{"lrg", "cpc", "grl", 'l', new ItemStack(Items.dye, 1, 4), 'r', Items.redstone, 'g', Items.glowstone_dust, 'p', "cpu", 'c', "circuitBasic"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedCircuit), new Object[]{"grl", "cpc", "lrg", 'l', new ItemStack(Items.dye, 1, 4), 'r', Items.redstone, 'g', Items.glowstone_dust, 'p', "cpu", 'c', "circuitBasic"}));

		// Lens
		GameRegistry.addShapedRecipe(new ItemStack(lens, 4), new Object[]{" g ", "g g", " g ", 'g', Blocks.glass});

		// Filament
		GameRegistry.addShapelessRecipe(new ItemStack(filament, 2), new Object[]{Items.redstone, Items.flint, IC2Items.getItem("copperCableItem")});
		// CPU
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cpu), new Object[]{" r ", "rcr", " r ", 'r', Items.redstone, 'c', "circuitBasic"}));

		// Rifle
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleBarrel), new Object[]{"sss", "   ", "sss", 's', "ingotSteel"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleTrigger), new Object[]{" ss", " s ", "  s", 's', "ingotSteel"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifle1), new Object[]{"lbe", "ssc", " ts", 'l', "lens", 'b', new ItemStack(rifleBarrel), 'e', IC2Items.getItem("energyCrystal"), 's', "plateSteel", 'c', "circuitAdvanced", 't', new ItemStack(rifleTrigger)}));

		// Rifle upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgradePlate, 2), new Object[]{"rir", "nsn", "rir", 'r', Items.redstone, 'i', "ingotIron", 'n', Items.gold_nugget, 's', "plateSteel"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleUpgrade), new Object[]{"cuc", 'u', new ItemStack(upgradePlate), 'c', "circuitBasic"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleUpgrade, 1, 1), new Object[]{"c", "b", 'b', new ItemStack(rifleUpgrade), 'c', IC2Items.getItem("reBattery")}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleUpgrade, 1, 2), new Object[]{"w", "b", 'b', new ItemStack(rifleUpgrade), 'w', IC2Items.getItem("waterCell")}));
		// GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleUpgrade, 1, 3), new Object[]{"l", "b", 'b', new ItemStack(rifleUpgrade), 'l', "lens"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleUpgrade, 1, 4), new Object[]{"f", "b", 'b', new ItemStack(rifleUpgrade), 'f', filament}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleUpgrade, 1, 5), new Object[]{"c", "b", 'b', new ItemStack(rifleUpgrade), 'c', cpu}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rifleUpgrade, 1, 6), new Object[]{"t", "b", 'b', new ItemStack(rifleUpgrade), 't', Blocks.tnt}));
	}
}
