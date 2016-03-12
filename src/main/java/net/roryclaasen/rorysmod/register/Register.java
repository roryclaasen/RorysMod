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
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.roryclaasen.rorysmod.core.RorysMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Register {

	private static int items, blocks, tileEntitys, entitys, recipies, dictionary, event;

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

	public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
		GameRegistry.registerTileEntity(tileEntityClass, id);
		tileEntitys++;
	}

	public static void registerTileEntityWithAlternatives(Class<? extends TileEntity> tileEntityClass, String id, String... alternatives) {
		GameRegistry.registerTileEntityWithAlternatives(tileEntityClass, id, alternatives);
		tileEntitys++;
	}

	public static void registerEntities(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		registerEntities(entityClass, entityName, entitys, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	@Deprecated
	public static void registerEntities(Class<? extends Entity> entityClass, String entityName, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, id, RorysMod.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		entitys++;
	}

	public static void registerEventBus(Object target) {
		MinecraftForge.EVENT_BUS.register(target);
		event++;
	}

	public static int getRegisteredItems() {
		return items;
	}

	public static int getRegisteredBlocks() {
		return blocks;
	}

	public static int getRegisteredTileEntities() {
		return tileEntitys;
	}

	public static int getRegisteredEntities() {
		return entitys;
	}

	public static int getRegisteredRecipies() {
		return recipies;
	}

	public static int getRegisteredInDictionary() {
		return dictionary;
	}

	public static int getRegisteredEvents() {
		return event;
	}
}
