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
package me.roryclaasen.rorysmod.core.register;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.recipe.RoryChargedShapedRecipe;
import me.roryclaasen.rorysmod.core.recipe.RoryShapedRecipe;
import me.roryclaasen.rorysmod.core.recipe.RoryChargedShapelessRecipe;
import me.roryclaasen.rorysmod.core.recipe.RoryShapelessRecipe;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

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
		GameRegistry.addRecipe(new RoryShapedRecipe(result, recipe));
		recipies++;
	}

	public static void addShapedChargedRecipie(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new RoryChargedShapedRecipe(result, recipe));
		recipies++;
	}

	public static void addShapelessChargedRecipie(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new RoryChargedShapelessRecipe(result, recipe));
		recipies++;
	}

	public static void addShapelessRecipie(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new RoryShapelessRecipe(result, recipe));
		recipies++;
	}

	public static void addSmeltingRecipie(ItemStack output, ItemStack input) {
		addSmeltingRecipie(input, output, 0F);
	}

	public static void addSmeltingRecipie(ItemStack output, ItemStack input, float xp) {
		GameRegistry.addSmelting(output, input, xp);
		recipies++;
	}

	public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput) {
		addPulverizerRecipe(energy, input, primaryOutput, null, 0);
	}

	public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
		if (input == null || primaryOutput == null) {
			return;
		}
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", energy);
		toSend.setTag("input", new NBTTagCompound());
		toSend.setTag("primaryOutput", new NBTTagCompound());
		if (secondaryOutput != null) {
			toSend.setTag("secondaryOutput", new NBTTagCompound());
		}
		input.writeToNBT(toSend.getCompoundTag("input"));
		primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));
		if (secondaryOutput != null) {
			secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
			toSend.setInteger("secondaryChance", secondaryChance);
		}
		boolean bool = FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);
		if (bool) recipies++;
	}

	public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput) {
		addSmelterRecipe(energy, primaryInput, secondaryInput, primaryOutput, null, 0);
	}

	public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("energy", energy);
		NBTTagCompound input1Compound = new NBTTagCompound();
		primaryInput.writeToNBT(input1Compound);
		data.setTag("primaryInput", input1Compound);

		NBTTagCompound input2Compound = new NBTTagCompound();
		secondaryInput.writeToNBT(input2Compound);
		data.setTag("secondaryInput", input2Compound);

		NBTTagCompound output1Compound = new NBTTagCompound();
		primaryOutput.writeToNBT(output1Compound);
		data.setTag("primaryOutput", output1Compound);

		if (secondaryOutput != null) {
			NBTTagCompound output2Compound = new NBTTagCompound();
			secondaryOutput.writeToNBT(output2Compound);
			data.setTag("secondaryOutput", output2Compound);

			data.setInteger("secondaryChance", secondaryChance);
		}
		boolean bool = FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", data);
		if (bool) recipies++;
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

		FMLCommonHandler.instance().bus().register(target);
	}

	public static void registerFMLEvent(Object target) {
		FMLCommonHandler.instance().bus().register(target);
		event++;
	}

	public static void registerGUI(IGuiHandler handler) {
		NetworkRegistry.INSTANCE.registerGuiHandler(RorysMod.instance, handler);
	}

	public static void registerGUI(Object mod, IGuiHandler handler) {
		NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
	}

	public static void registerGlobalEntityID(Class<? extends Entity> entityClass, String entityName) {
		registerGlobalEntityID(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId());
	}

	@Deprecated
	public static void registerGlobalEntityID(Class<? extends Entity> entityClass, String entityName, int id) {
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
	}

	public static void registerEntityRenderingHandler(Class<? extends Entity> entityClass, Render renderer) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
	}

	public static void registerItemRenderer(Item item, IItemRenderer renderer) {
		MinecraftForgeClient.registerItemRenderer(item, renderer);
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
