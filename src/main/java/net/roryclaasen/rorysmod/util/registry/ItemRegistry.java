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
package net.roryclaasen.rorysmod.util.registry;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import net.roryclaasen.rorysmod.util.RMLog;
import cpw.mods.fml.common.registry.GameRegistry;

@SuppressWarnings("unchecked")
public final class ItemRegistry {

	/**
	 * Registers a new item and overrides the old one if present.
	 * 
	 * @param id the id
	 * @param item the item
	 */
	public void register(String id, Item item) {
		if (!id.startsWith("minecraft:")) {
			GameRegistry.registerItem(item, id);
		} else {
			/**
			 * Currently is the {@link GameRegistry#addSubstitutionAlias(...)} not working. :/
			 */

			/**
			try {
				GameRegistry.addSubstitutionAlias(id, Type.Item, item);
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/

			RegistryNamespaced registry = Item.itemRegistry;

			try {
				Item item0 = (Item) registry.getObject(id);
				Integer id0 = registry.getIDForObject(item0);

				Map<Object, Object> map0 = null;
				ObjectIntIdentityMap map1 = null;

				Field field0 = Fields.findField(RegistrySimple.class, Map.class, 0);
				field0.setAccessible(true);

				Field field1 = Fields.findField(RegistryNamespaced.class, ObjectIntIdentityMap.class, 0);
				field1.setAccessible(true);

				map0 = (Map<Object, Object>) field0.get(registry);
				map1 = (ObjectIntIdentityMap) field1.get(registry);

				map0.remove(id);
				map0.put(id, item);
				map0.put("removed:" + id.replace("minecraft:", ""), item0);
				map1.func_148746_a(item, id0);

				
				item0.setCreativeTab(null);

				for (Field field : Item.class.getFields()) {
					int modifiers = field.getModifiers();

					if (Modifier.isStatic(modifiers)) {
						try {
							if (field.get(null).equals(item0)) {
								Field field2 = Field.class.getDeclaredField("modifiers");
								field2.setAccessible(true);
								field2.set(field, modifiers & ~Modifier.FINAL);

								field.setAccessible(true);
								field.set(null, item);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				Field field2 = Fields.findField(StatCrafting.class, Item.class, 0);
				field2.setAccessible(true);

				List<StatCrafting> list = StatList.itemStats;
				Iterator<StatCrafting> it = list.iterator();

				while (it.hasNext()) {
					StatCrafting stat = it.next();

					if (field2.get(stat) == item0) {
						Field field3 = Field.class.getDeclaredField("modifiers");
						field3.setAccessible(true);
						field3.set(field2, field2.getModifiers() & ~Modifier.FINAL);

						field2.setAccessible(true);
						field2.set(stat, item);
					}
				}
				RMLog.info("Replaced Item '" + id + "' with " + item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}