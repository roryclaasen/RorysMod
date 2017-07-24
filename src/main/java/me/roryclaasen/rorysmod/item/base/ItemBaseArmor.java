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
package me.roryclaasen.rorysmod.item.base;

import me.roryclaasen.rorysmod.core.RorysMod;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemBaseArmor extends ItemArmor {

	private final String internalName;

	public ItemBaseArmor(String unlocalizedName, ArmorMaterial material, int type) {
		super(material, 0, type);

		this.setUnlocalizedName(RorysMod.MODID + "_" + unlocalizedName);
		this.setTextureName(RorysMod.MODID + ":" + unlocalizedName);
		this.setCreativeTab(RorysMod.tab);

		this.internalName = unlocalizedName;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return RorysMod.MODID + ":textures/models/armor/" + this.getArmorMaterial().name() + "_layer_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}

	public String getName() {
		return internalName;
	}
}
