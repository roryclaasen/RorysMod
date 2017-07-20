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
package me.roryclaasen.rorysmod.core.intergrations.nei;

import java.util.ArrayList;
import java.util.List;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.recipe.RoryShapelessRecipe;
import me.roryclaasen.rorysmod.util.RMLog;
import me.roryclaasen.rorysmod.util.RecipeUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;

public class RoryShapelessRecipeHandler extends ShapelessRecipeHandler {

	@Override
	public String getRecipeName() {
		return RorysMod.NAME + " " + StatCollector.translateToLocal("recipie.rorysmod.shapeless");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("crafting") && getClass() == RoryShapelessRecipeHandler.class) {
			List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();

			for (IRecipe irecipe : allrecipes) {
				if (irecipe instanceof RoryShapelessRecipe) {
					RoryShapelessRecipe rorysmodRecipe = (RoryShapelessRecipe) irecipe;
					CachedRoryShapelessRecipe recipe = new CachedRoryShapelessRecipe(rorysmodRecipe);

					arecipes.add(recipe);
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();

		for (IRecipe irecipe : allrecipes) {
			if (irecipe instanceof RoryShapelessRecipe && RecipeUtils.areItemsEqualForCrafting(irecipe.getRecipeOutput(), result)) {
				RoryShapelessRecipe rorysmodRecipe = (RoryShapelessRecipe) irecipe;
				CachedRoryShapelessRecipe recipe = new CachedRoryShapelessRecipe(rorysmodRecipe);

				arecipes.add(recipe);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();

		for (IRecipe irecipe : allrecipes) {
			if (irecipe instanceof RoryShapelessRecipeHandler) {
				RoryShapelessRecipe rorysmodRecipe = (RoryShapelessRecipe) irecipe;
				CachedRoryShapelessRecipe recipe = new CachedRoryShapelessRecipe(rorysmodRecipe);
				RMLog.info(ingredient + " -> " + recipe.ingredients);
				if (recipe.contains(recipe.ingredients, ingredient)) {
					recipe.setIngredientPermutation(recipe.ingredients, ingredient);
					arecipes.add(recipe);
				}
			}
		}
	}

	public class CachedRoryShapelessRecipe extends CachedRecipe {
		public ArrayList<PositionedStack> ingredients;
		public PositionedStack result;

		public CachedRoryShapelessRecipe(RoryShapelessRecipe recipe) {
			result = new PositionedStack(recipe.getRecipeOutput(), 119, 24);
			ingredients = new ArrayList<PositionedStack>();
			setIngredients(recipe.getInput());
		}

		public void setIngredients(List<?> items) {
			ingredients.clear();

			for (int x = 0; x < items.size(); x++) {
				PositionedStack stack = new PositionedStack(items.get(x), 25 + stackorder[x][0] * 18, 6 + stackorder[x][1] * 18);
				stack.setMaxSize(1);
				ingredients.add(stack);
			}
		}

		public void setResult(ItemStack output) {
			result = new PositionedStack(output, 119, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}
	}
}
