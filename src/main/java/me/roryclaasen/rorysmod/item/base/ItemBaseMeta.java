/*
Copyright 2016-2017 Rory Claasen

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
package me.roryclaasen.rorysmod.item.base;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import me.roryclaasen.rorysmod.core.RorysMod;

public class ItemBaseMeta extends ItemBase {

	public IIcon[] icons;

	public ItemBaseMeta(String unlocalizedName, int size) {
		super(unlocalizedName);
		this.icons = new IIcon[size];
		this.setHasSubtypes(true);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < icons.length; i++) {
			ItemStack stack = new ItemStack(item, 1, i);
			list.add(stack);
		}
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		for (int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon(RorysMod.MODID + ":" + getName() + "_" + i);
		}
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta >= icons.length) meta = 0;
		return this.icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
}
