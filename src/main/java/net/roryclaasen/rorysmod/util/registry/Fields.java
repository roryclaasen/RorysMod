package net.roryclaasen.rorysmod.util.registry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Fields {

	private Fields() {}

	/**
	 * Finds the object of a declared field with a specific type in the
	 * target class. With a specific index.
	 * 
	 * @param target
	 *            the target class
	 * @param fieldType
	 *            the field type
	 * @param targetObject
	 *            the target object you want to retrieve the object of
	 * @param index
	 *            the field index
	 * @return the object
	 */
	public static Object findFieldAndGet(Class<?> target, Class<?> fieldType, Object targetObject, int index) {
		for (Field field : target.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(fieldType)) {
				if (index == 0) {
					try {
						return field.get(targetObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				index--;
			}
		}
		return null;
	}

	/**
	 * Finds the objects of all declared fields with a specific type in the
	 * target class. With a specific index.
	 * 
	 * @param target
	 *            the target class
	 * @param fieldType
	 *            the field type
	 * @param targetObject
	 *            the target object you want to retrieve the object of
	 * @return the objects
	 */
	public static Object[] findFieldsAndGet(Class<?> target, Class<?> fieldType, Object targetObject) {
		List<Object> list = new ArrayList<Object>();
		for (Field field : target.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(fieldType)) {
				try {
					list.add(field.get(targetObject));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list.toArray(new Object[]{});
	}

	/**
	 * Finds a declared field of a specific type in the target class. With a specific index.
	 * 
	 * @param target
	 *            the target class
	 * @param fieldType
	 *            the field type
	 * @param index
	 *            the field index
	 * @return the field
	 */
	public static Field findField(Class<?> target, Class<?> fieldType, int index) {
		for (Field field : target.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(fieldType)) {
				if (index == 0) {
					return field;
				}
				index--;
			}
		}
		return null;
	}

	/**
	 * Finds all the declared fields of a specific type in the target class.
	 * 
	 * @param target
	 *            the target class
	 * @param fieldType
	 *            the field type
	 * @return the fields
	 */
	public static Field[] findFields(Class<?> target, Class<?> fieldType) {
		return Fields.findFields(target, fieldType, 0);
	}

	/**
	 * Finds all the declared fields of a specific type in the target class.
	 * 
	 * @param target
	 *            the target class
	 * @param fieldType
	 *            the field type
	 * @param depth
	 *            the depth you want to check for underlying classes their fields
	 * @return the fields
	 */
	public static Field[] findFields(Class<?> target, Class<?> fieldType, int depth) {
		List<Field> list = new ArrayList<Field>();
		while (target != null && target != Object.class) {
			for (Field field : target.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getType().isAssignableFrom(fieldType)) {
					list.add(field);
				}
			}
			target = target.getSuperclass();
			if (depth != -1 && depth-- == 0) {
				break;
			}
		}
		return list.toArray(new Field[]{});
	}

}
