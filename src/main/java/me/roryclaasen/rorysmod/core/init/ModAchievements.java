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
package me.roryclaasen.rorysmod.core.init;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import me.roryclaasen.rorysmod.core.RorysGlobal;
import me.roryclaasen.rorysmod.core.RorysMod;
import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class ModAchievements implements ModInterface {

	public AchievementPage page;
	private static final String BASE_ID = "achievement.";

	private List<Achievement> all;

	public Achievement solderDust, solderArmor, solderArmorFull;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		all = new ArrayList<Achievement>();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		solderDust = createAchievement("solder_dust", 1, -3, RorysMod.items.solderDust, null);
		solderArmor = createAchievement("solder_arm", 2, -4, RorysMod.items.solderChest, solderDust);
		solderArmorFull = createAchievement("solder_armFull", 4, -4, RorysMod.items.solderHelm, solderArmor).setSpecial();

		page = new AchievementPage(RorysGlobal.NAME, (Achievement[]) all.toArray(new Achievement[] {}));
		AchievementPage.registerAchievementPage(page);
	}

	@Override
	public void postinit(FMLPostInitializationEvent event) {}

	private Achievement createAchievement(String name, int x, int y, Item item, Achievement parent) {
		Achievement temp = new Achievement(BASE_ID + name, name, x, y, item, parent);
		all.add(temp);
		return temp;
	}

	public List<Achievement> getAllAchivements() {
		return page.getAchievements();
	}
}
