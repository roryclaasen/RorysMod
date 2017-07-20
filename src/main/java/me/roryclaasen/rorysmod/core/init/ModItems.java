/*
 * Copyright 2016-2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmod.core.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import me.roryclaasen.rorysmod.core.register.Register;
import me.roryclaasen.rorysmod.item.ItemCoil;
import me.roryclaasen.rorysmod.item.ItemDust;
import me.roryclaasen.rorysmod.item.ItemIngot;
import me.roryclaasen.rorysmod.item.ItemPlate;
import me.roryclaasen.rorysmod.item.ItemRifleUpgrade;
import me.roryclaasen.rorysmod.item.base.ItemBase;
import me.roryclaasen.rorysmod.item.tools.ItemRifle;
import me.roryclaasen.rorysmod.item.tools.ItemSolderingIron;
import me.roryclaasen.rorysmod.util.RMLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems implements ModInterface {

	public Item steelIngot, steelDust, steelPlate;
	public Item carbonIngot;
	public Item rifle1, rifle2, rifle3, rifle4, rifle5;
	public Item laserBolt, rifleBarrel, rifleTrigger;
	public Item rifleUpgrade, upgradePlate;
	public Item circuit, advancedCircuit;
	public Item blankCircuit, blankAdvancedCircuit;
	public Item lens, filament, cpu;
	public Item solderingIron;
	public Item solderDust, solderIngot, solderPlate, solderWire, solderCoil;

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
		blankCircuit = new ItemBase("blankCircuit");
		blankAdvancedCircuit = new ItemBase("blankAdvancedCircuit");
		lens = new ItemBase("lens");
		filament = new ItemBase("filament");
		cpu = new ItemBase("cpu");
		solderingIron = new ItemSolderingIron("solderingIron");
		solderDust = new ItemDust("dustSolder");
		solderIngot = new ItemIngot("ingotSolder");
		solderPlate = new ItemPlate("plateSolder");
		solderWire = new ItemBase("wireSolder");
		solderCoil = new ItemCoil("coilSolder");
	}

	public void register(FMLPreInitializationEvent event) {
		RMLog.info("Registering Items");

		Register.registerItem(steelIngot);
		Register.registerItem(steelDust);
		Register.registerItem(steelPlate);
		Register.registerItem(carbonIngot);
		Register.registerItem(rifle1);
		Register.registerItem(rifle2);
		Register.registerItem(rifle3);
		Register.registerItem(rifle4);
		Register.registerItem(rifle5);
		Register.registerItem(rifleBarrel);
		Register.registerItem(rifleTrigger);
		Register.registerItem(laserBolt);
		Register.registerItem(rifleUpgrade);
		Register.registerItem(upgradePlate);
		Register.registerItem(circuit);
		Register.registerItem(advancedCircuit);
		Register.registerItem(blankCircuit);
		Register.registerItem(blankAdvancedCircuit);
		Register.registerItem(lens);
		Register.registerItem(filament);
		Register.registerItem(cpu);
		Register.registerItem(solderingIron);
		Register.registerItem(solderDust);
		Register.registerItem(solderIngot);
		Register.registerItem(solderPlate);
		Register.registerItem(solderWire);
		Register.registerItem(solderCoil);

		Register.registerDictionary("ingotSteel", steelIngot);
		Register.registerDictionary("dustSteel", steelDust);
		Register.registerDictionary("plateSteel", steelPlate);
		Register.registerDictionary("ingotCarbon", carbonIngot);
		Register.registerDictionary("circuitBasic", circuit);
		Register.registerDictionary("circuitAdvanced", advancedCircuit);
		Register.registerDictionary("lens", lens);
		Register.registerDictionary("filament", filament);
		Register.registerDictionary("cpu", cpu);
		Register.registerDictionary("ingotSolder", solderIngot);
		Register.registerDictionary("dustSolder", solderDust);
		Register.registerDictionary("plateSolder", solderPlate);
		Register.registerDictionary("wireSolder", solderWire);
		Register.registerDictionary("coilSolder", new ItemStack(solderCoil, 1, OreDictionary.WILDCARD_VALUE));
		Register.registerDictionary("solder", solderWire);
		Register.registerDictionary("solder", new ItemStack(solderCoil, 1, OreDictionary.WILDCARD_VALUE));

		Register.registerDictionary("solderingIron", new ItemStack(solderingIron, 1, OreDictionary.WILDCARD_VALUE));
	}

	@Override
	public void init(FMLInitializationEvent event) {
		ItemStack toolSolderingIron = new ItemStack(solderingIron, 1, OreDictionary.WILDCARD_VALUE);

		ItemStack forgeHammer = IC2Items.getItem("ForgeHammer").copy();
		forgeHammer.setItemDamage(OreDictionary.WILDCARD_VALUE);

		ItemStack wireCutters = IC2Items.getItem("cutter").copy();
		wireCutters.setItemDamage(OreDictionary.WILDCARD_VALUE);

		// Carbon
		Register.addShapelessRecipie(new ItemStack(carbonIngot), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), "ingotIron");
		Register.addSmeltingRecipie(new ItemStack(carbonIngot), new ItemStack(steelIngot), 0.1f);

		// Steel
		Register.addShapelessRecipie(new ItemStack(steelDust, 2), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), "dustIron");
		Register.addShapelessRecipie(new ItemStack(steelPlate), "ingotSteel", forgeHammer);
		Register.addShapelessRecipie(new ItemStack(steelIngot, 9), "blockSteel");
		Register.addSmeltingRecipie(new ItemStack(steelDust), new ItemStack(steelIngot), 0.1f);

		Recipes.metalformerRolling.addRecipe(new RecipeInputItemStack(new ItemStack(steelIngot)), null, new ItemStack(steelPlate));
		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(steelIngot)), null, new ItemStack(steelDust));
		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(steelPlate)), null, new ItemStack(steelDust));

		// Circuit
		Register.addShapedRecipie(new ItemStack(blankCircuit), new Object[] { " r ", "gsg", " r ", 'r', Items.redstone, 's', "plateSteel", 'g', Items.gold_nugget });
		Register.addShapelessChargedRecipie(new ItemStack(circuit), new Object[] { blankCircuit, "solder", toolSolderingIron });

		Register.addShapelessChargedRecipie(new ItemStack(blankAdvancedCircuit), new Object[] { blankCircuit, "cpu", "solder", toolSolderingIron });
		Register.addShapelessChargedRecipie(new ItemStack(advancedCircuit), new Object[] { blankAdvancedCircuit, "solder", toolSolderingIron });

		// Lens
		Register.addShapedRecipie(new ItemStack(lens, 4), new Object[] { " g ", "g g", " g ", 'g', Blocks.glass });

		// Filament
		Register.addShapelessRecipie(new ItemStack(filament, 2), new Object[] { IC2Items.getItem("copperCableItem"), "solder", toolSolderingIron });

		// CPU
		Register.addShapedChargedRecipie(new ItemStack(cpu), new Object[] { " r ", "rcr", " r ", 'r', Items.redstone, 'c', "circuitBasic" });

		// Rifle
		Register.addShapedRecipie(new ItemStack(rifleBarrel), new Object[] { "sss", "   ", "sss", 's', "ingotSteel" });
		Register.addShapedRecipie(new ItemStack(rifleTrigger), new Object[] { " ss", " s ", "  s", 's', "ingotSteel" });

		Register.addShapedRecipie(new ItemStack(rifle1), new Object[] { "lbe", "ssc", " ts", 'l', "lens", 'b', new ItemStack(rifleBarrel), 'e', IC2Items.getItem("energyCrystal"), 's', "plateSteel", 'c', "circuitAdvanced", 't', new ItemStack(rifleTrigger) });

		Register.addShapelessRecipie(new ItemStack(rifle2), new Object[] { rifle1, new ItemStack(upgradePlate), IC2Items.getItem("energyCrystal") });
		Register.addShapelessRecipie(new ItemStack(rifle3), new Object[] { rifle2, new ItemStack(upgradePlate), IC2Items.getItem("energyCrystal") });
		Register.addShapelessRecipie(new ItemStack(rifle4), new Object[] { rifle3, new ItemStack(upgradePlate), new ItemStack(upgradePlate), IC2Items.getItem("energyCrystal") });
		Register.addShapelessRecipie(new ItemStack(rifle5), new Object[] { rifle4, new ItemStack(upgradePlate), new ItemStack(upgradePlate), IC2Items.getItem("energyCrystal") });

		// Rifle upgrade
		Register.addShapedRecipie(new ItemStack(upgradePlate, 2), new Object[] { "rir", "nsn", "rir", 'r', Items.redstone, 'i', "ingotIron", 'n', Items.gold_nugget, 'c', "cpu" });
		Register.addShapedRecipie(new ItemStack(rifleUpgrade), new Object[] { "cuc", 'u', new ItemStack(upgradePlate), 'c', "circuitBasic" });
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 1), new Object[] { "c", "b", 'b', new ItemStack(rifleUpgrade), 'c', IC2Items.getItem("reBattery") });
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 2), new Object[] { "w", "b", 'b', new ItemStack(rifleUpgrade), 'w', IC2Items.getItem("waterCell") });
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 3), new Object[] { "l", "b", 'b', new ItemStack(rifleUpgrade), 'l', "lens" });
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 4), new Object[] { "f", "b", 'b', new ItemStack(rifleUpgrade), 'f', filament });
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 5), new Object[] { "c", "b", 'b', new ItemStack(rifleUpgrade), 'c', cpu });
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 6), new Object[] { "t", "b", 'b', new ItemStack(rifleUpgrade), 't', Blocks.tnt });
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 7), new Object[] { "f", "b", 'b', new ItemStack(rifleUpgrade), 'f', Items.flint_and_steel });

		// Soldering Iron
		Register.addShapedRecipie(new ItemStack(solderingIron), new Object[] { "  i", " i ", "c  ", 'i', "ingotIron", 'c', IC2Items.getItem("insulatedCopperCableItem") });

		// Solder
		Register.addShapelessRecipie(new ItemStack(solderDust, 5), new Object[] { "dustTin", "dustTin", "dustTin", "dustLead", "dustLead" });
		Register.addSmeltingRecipie(new ItemStack(solderDust), new ItemStack(solderIngot), 0.1f);
		Register.addShapelessRecipie(new ItemStack(solderIngot, 9), "blockSolder");
		Recipes.metalformerRolling.addRecipe(new RecipeInputItemStack(new ItemStack(solderIngot)), null, new ItemStack(solderPlate));
		Recipes.metalformerCutting.addRecipe(new RecipeInputItemStack(new ItemStack(solderPlate)), null, new ItemStack(solderWire, 3));
		Recipes.metalformerExtruding.addRecipe(new RecipeInputItemStack(new ItemStack(solderIngot)), null, new ItemStack(solderWire, 3));
		Register.addShapelessRecipie(new ItemStack(solderPlate), "ingotSolder", forgeHammer);
		Register.addShapelessRecipie(new ItemStack(solderWire, 2), "plateSolder", wireCutters);
		Register.addShapedRecipie(new ItemStack(solderCoil), new Object[] { "sss", "sis", "sss", 's', "wireSolder", 'i', "ingotIron" });
		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(solderIngot)), null, new ItemStack(solderDust));
		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(solderPlate)), null, new ItemStack(solderDust));

		Recipes.metalformerCutting.addRecipe(new RecipeInputItemStack(new ItemStack(solderCoil)), null, new ItemStack(solderWire, 8));
		Register.addShapelessRecipie(new ItemStack(solderWire, 8), "coilSolder", wireCutters);
	}

	@Override
	public void postinit(FMLPostInitializationEvent event) {
		ItemStack tin = OreDictionary.getOres("dustTin").get(0);
		tin.stackSize = 3;
		ItemStack lead = OreDictionary.getOres("dustLead").get(0);
		lead.stackSize = 2;
		// Solder
		Register.addPulverizerRecipe(2400, new ItemStack(solderIngot), new ItemStack(solderDust));
		Register.addSmelterRecipe(4000, tin, lead, new ItemStack(solderIngot, 7));
	}
}
