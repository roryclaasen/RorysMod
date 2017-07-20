/*
 * Copyright 2017 Rory Claasen
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
package me.roryclaasen.rorysmod.core.intergrations;

import cpw.mods.fml.common.Loader;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.util.RMLog;
import mekanism.api.infuse.InfuseObject;
import mekanism.api.infuse.InfuseRegistry;
import mekanism.api.infuse.InfuseType;
import mekanism.api.recipe.RecipeHelper;
import net.minecraft.item.ItemStack;

@SuppressWarnings("deprecation")
public class MekanismPlugin {
	public static void load() {
		RMLog.info("Attempting to load Mekanism Intergration");
		if (Loader.isModLoaded("Mekanism")) {
			RMLog.info("Mekanism found!");
			intergrate();
		} else RMLog.info("Mekanism not found. Skipping");
	}

	private static void intergrate() {
		InfuseType solderInfuse = new InfuseType("SOLDER", RorysMod.MODID + ":infuse/solder").setUnlocalizedName("solder");
		InfuseRegistry.registerInfuseType(solderInfuse);
		InfuseRegistry.registerInfuseObject(new ItemStack(RorysMod.items.solderWire), new InfuseObject(solderInfuse, 1));
		InfuseRegistry.registerInfuseObject(new ItemStack(RorysMod.items.solderCoil), new InfuseObject(solderInfuse, 8));

		RecipeHelper.addMetallurgicInfuserRecipe(solderInfuse, 1, new ItemStack(RorysMod.items.blankCircuit), new ItemStack(RorysMod.items.circuit));
		RecipeHelper.addMetallurgicInfuserRecipe(solderInfuse, 1, new ItemStack(RorysMod.items.blankAdvancedCircuit), new ItemStack(RorysMod.items.advancedCircuit));
	}
}
