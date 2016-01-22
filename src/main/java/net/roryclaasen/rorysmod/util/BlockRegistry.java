package net.roryclaasen.rorysmod.util;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import net.roryclaasen.rorysmod.RMLog;

@SuppressWarnings("unchecked")
public final class BlockRegistry {

	/**
	 * Registers a new block and overrides the old one if present.
	 * 
	 * @param id the id
	 * @param block the block
	 */
	public void register(String id, Block block) {
		if (!id.startsWith("minecraft:")) {
			GameRegistry.registerBlock(block, id);
		} else {
			/**
			 * Currently is the {@link GameRegistry#addSubstitutionAlias(...)} not working. :/
			 */

			/**
			try {
				GameRegistry.addSubstitutionAlias(id, Type.Block, item);
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/

			RegistryNamespaced registry = Block.blockRegistry;

			try {
				Block block0 = (Block) registry.getObject(id);
				Integer id0 = registry.getIDForObject(block0);

				Map<Object, Object> map0 = null;
				ObjectIntIdentityMap map1 = null;

				Field field0 = Fields.findField(RegistrySimple.class, Map.class, 0);
				field0.setAccessible(true);

				Field field1 = Fields.findField(RegistryNamespaced.class, ObjectIntIdentityMap.class, 0);
				field1.setAccessible(true);

				map0 = (Map<Object, Object>) field0.get(registry);
				map1 = (ObjectIntIdentityMap) field1.get(registry);

				map0.remove(id);
				map0.put(id, block);
				map0.put("removed:" + id.replace("minecraft:", ""), block0);
				map1.func_148746_a(block, id0);

				
				block0.setCreativeTab(null);

				for (Field field : Blocks.class.getFields()) {
					int modifiers = field.getModifiers();

					if (Modifier.isStatic(modifiers)) {
						try {
							if (field.get(null).equals(block0)) {
								Field field2 = Field.class.getDeclaredField("modifiers");
								field2.setAccessible(true);
								field2.set(field, modifiers & ~Modifier.FINAL);

								field.setAccessible(true);
								field.set(null, block);
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

					if (field2.get(stat) == block0) {
						Field field3 = Field.class.getDeclaredField("modifiers");
						field3.setAccessible(true);
						field3.set(field2, field2.getModifiers() & ~Modifier.FINAL);

						field2.setAccessible(true);
						field2.set(stat, block);
					}
				}
				RMLog.info("Replaced block '" + id + "' with " + block);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}