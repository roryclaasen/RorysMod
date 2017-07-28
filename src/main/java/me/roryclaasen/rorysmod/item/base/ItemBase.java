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
package me.roryclaasen.rorysmod.item.base;

import me.roryclaasen.rorysmod.core.RorysGlobal;
import me.roryclaasen.rorysmod.core.RorysMod;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	private final String internalName;

	public ItemBase(String unlocalizedName) {
		super();
		this.setUnlocalizedName(RorysGlobal.MODID + "_" + unlocalizedName);
		this.setTextureName(RorysGlobal.MODID + ":" + unlocalizedName);
		this.setCreativeTab(RorysMod.creativeTab);

		this.internalName = unlocalizedName;
	}

	public String getName() {
		return internalName;
	}
}
