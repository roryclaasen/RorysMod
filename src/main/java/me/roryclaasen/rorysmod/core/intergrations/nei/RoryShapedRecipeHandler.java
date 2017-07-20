package me.roryclaasen.rorysmod.core.intergrations.nei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapedRecipeHandler;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.recipe.RoryShapedRecipe;
import me.roryclaasen.rorysmod.util.RecipeUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;

public class RoryShapedRecipeHandler extends ShapedRecipeHandler {
	@Override
	public String getRecipeName() {
		return RorysMod.NAME + " " + StatCollector.translateToLocal("recipie.rorysmod.shaped");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("crafting") && getClass() == RoryShapedRecipeHandler.class) {
			List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();

			for (IRecipe irecipe : allrecipes) {
				if (irecipe instanceof RoryShapedRecipe) {
					RoryShapedRecipe rorysmodRecipe = (RoryShapedRecipe) irecipe;
					CachedShapedRorysModRecipe recipe = new CachedShapedRorysModRecipe(rorysmodRecipe);

					recipe.computeVisuals();
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
			if (irecipe instanceof RoryShapedRecipe && RecipeUtils.areItemsEqualForCrafting(irecipe.getRecipeOutput(), result)) {
				RoryShapedRecipe rorysmodRecipe = (RoryShapedRecipe) irecipe;
				CachedShapedRorysModRecipe recipe = new CachedShapedRorysModRecipe(rorysmodRecipe);

				recipe.computeVisuals();
				arecipes.add(recipe);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();

		for (IRecipe irecipe : allrecipes) {
			if (irecipe instanceof RoryShapedRecipe) {
				RoryShapedRecipe rorysmodRecipe = (RoryShapedRecipe) irecipe;
				CachedShapedRorysModRecipe recipe = new CachedShapedRorysModRecipe(rorysmodRecipe);

				recipe.computeVisuals();

				if (recipe.contains(recipe.ingredients, ingredient)) {
					recipe.setIngredientPermutation(recipe.ingredients, ingredient);
					arecipes.add(recipe);
				}
			}
		}
	}

	public class CachedShapedRorysModRecipe extends CachedRecipe {
		public ArrayList<PositionedStack> ingredients;
		public PositionedStack result;

		public CachedShapedRorysModRecipe(RoryShapedRecipe recipe) {
			result = new PositionedStack(recipe.getRecipeOutput(), 119, 24);
			ingredients = new ArrayList<PositionedStack>();
			setIngredients(recipe.getWidth(), recipe.getHeight(), recipe.getInput());
		}

		public void setIngredients(int width, int height, Object[] items) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (items[y * width + x] == null) continue;
					PositionedStack stack = new PositionedStack(items[y * width + x], 25 + x * 18, 6 + y * 18);
					stack.setMaxSize(1);
					ingredients.add(stack);
				}
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}

		public void computeVisuals() {
			for (PositionedStack p : ingredients) {
				p.generatePermutations();
			}
		}

		@Override
		public boolean contains(Collection<PositionedStack> ingredients, ItemStack ingredient) {
			for (PositionedStack stack : ingredients) {
				for (ItemStack item : stack.items) {
					if (RecipeUtils.areItemsEqualForCrafting(item, ingredient)) {
						return true;
					}
				}
			}

			return false;
		}
	}
}
