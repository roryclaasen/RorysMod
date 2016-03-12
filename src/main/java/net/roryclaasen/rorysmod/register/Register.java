/*
Copyright 2016 Rory Claasen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package net.roryclaasen.rorysmod.register;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Register {

	private static int items, blocks, recipies, dictionary;

	private Register() {}

	public static void registerDictionary(String name, ItemStack item) {
		OreDictionary.registerOre(name, item);
		dictionary++;
	}

	public static void registerDictionary(String name, Item item) {
		OreDictionary.registerOre(name, item);
		dictionary++;
	}

	public static void registerDictionary(String name, Block block) {
		OreDictionary.registerOre(name, block);
		dictionary++;
	}

	public static void registerItem(Item item) {
		registerItem(item, item.getUnlocalizedName());
	}

	public static void registerItem(Item item, String name) {
		GameRegistry.registerItem(item, name);
		items++;
	}

	public static void registerBlock(Block block) {
		registerBlock(block, block.getUnlocalizedName());
	}

	public static void registerBlock(Block block, String name) {
		GameRegistry.registerBlock(block, name);
		blocks++;
	}

	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass) {
		registerBlock(block, itemclass, block.getUnlocalizedName());
	}

	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name) {
		GameRegistry.registerBlock(block, itemclass, name);
		blocks++;
	}

	public static void addShapedRecipie(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new ShapedOreRecipe(result, recipe));
		recipies++;
	}

	public static void addShaplessRecipie(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(result, recipe));
		recipies++;
	}

	public static void addSmeltingRecipie(ItemStack output, ItemStack input) {
		addSmeltingRecipie(input, output, 0F);
	}

	public static void addSmeltingRecipie(ItemStack output, ItemStack input, float xp) {
		GameRegistry.addSmelting(output, input, xp);
		recipies++;
	}

	public static int getRegisteredItems() {
		return items;
	}

	public static int getRegisteredBlocks() {
		return blocks;
	}

	public static int getRegisteredRecipies() {
		return recipies;
	}

	public static int getRegisteredInDictionary() {
		return dictionary;
	}
}
