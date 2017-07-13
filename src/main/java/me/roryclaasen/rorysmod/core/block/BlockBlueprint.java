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
package me.roryclaasen.rorysmod.core.block;

import net.minecraft.block.material.Material;

public class BlockBlueprint extends BlockBaseMeta {

	public BlockBlueprint(Material material, String unlocalizedName) {
		super(material, unlocalizedName, 5);
		this.setStepSound(soundTypeMetal);
		this.setResistance(15.0F);
		this.setHardness(12.0F);
		this.setHarvestLevel("pickaxe", 2);
	}
}
