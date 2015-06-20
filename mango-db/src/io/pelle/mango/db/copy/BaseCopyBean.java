package io.pelle.mango.db.copy;

import io.pelle.mango.client.base.vo.IChangeTracker;
import io.pelle.mango.client.base.vo.IHasChangeTracker;
import io.pelle.mango.client.base.vo.IVOEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;

public abstract class BaseCopyBean {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getAnnotation(Class<?> clazz, String fieldName, Class annotationClass) {
		Field field = getDeclaredField(clazz, fieldName);

		if (field != null) {
			return field.getAnnotation(annotationClass);
		}
		{
			return null;
		}
	}

	public static Field getDeclaredField(Class<?> clazz, String fieldName) {
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		return null;
	}

	public static boolean hasAnnotation(Class<?> clazz, String fieldName, Class<?> annotationClass) {
		return getAnnotation(clazz, fieldName, annotationClass) != null;
	}

	private final List<IFieldCopyHandler> fieldCopyHandlers = new ArrayList<IFieldCopyHandler>();

	public Object copyObject(Object sourceObject, Class<?> destClass, boolean clearChanges) {
		return copyObject(sourceObject, destClass, new HashMap<Object, Object>(), null, new ArrayList<String>(), clearChanges);
	}

	public Object copyObject(Object sourceObject, Class<?> destClass) {
		return copyObject(sourceObject, destClass, true);
	}

	public Object copyObject(Object sourceObject, Class<?> destClass, String[] attributesToOmit) {
		return copyObject(sourceObject, destClass, new HashMap<Object, Object>(), null, Arrays.asList(attributesToOmit));
	}

	public Object copyObject(Object sourceObject, Class<?> destClass, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations) {
		return copyObject(sourceObject, destClass, classLoadAssociations, false, new String[0]);
	}

	public Object copyObject(Object sourceObject, Class<?> destClass, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations, boolean clearChanges, String[] attributesToOmit) {
		return copyObject(sourceObject, destClass, new HashMap<Object, Object>(), classLoadAssociations, Arrays.asList(attributesToOmit), clearChanges);
	}

	public Object copyObject(Object sourceObject) {
		return copyObject(sourceObject, getMappedTargetType(sourceObject.getClass()), new HashMap<Object, Object>(), null, new ArrayList<String>());
	}

	public void addFieldCopyHandler(IFieldCopyHandler fieldCopyHandler) {
		this.fieldCopyHandlers.add(fieldCopyHandler);
	}

	private Object copyObject(Object sourceObject, Class<?> destClass, Map<Object, Object> visited, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations, List<String> attributesToOmit) {
		return copyObject(sourceObject, destClass, visited, classLoadAssociations, attributesToOmit, false);
	}

	private Object copyObject(Object sourceObject, Class<?> destClass, Map<Object, Object> visited, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations, List<String> attributesToOmit, boolean clearChanges) {

		Set<String> loadAssociations = null;

		if (classLoadAssociations != null) {

			loadAssociations = new HashSet<String>();

			if (classLoadAssociations.containsKey(sourceObject.getClass())) {
				loadAssociations = classLoadAssociations.get(sourceObject.getClass());
			}

			if (classLoadAssociations.containsKey(destClass)) {
				loadAssociations = classLoadAssociations.get(destClass);
			}
		}

		if (sourceObject == null) {
			return null;
		}

		Object targetObject = null;
		try {
			targetObject = ConstructorUtils.invokeConstructor(destClass, new Object[] {});
		} catch (Exception e) {
			throw new RuntimeException(String.format("error invoking constructor for '%s'", destClass.getName()), e);
		}

		visited.put(sourceObject, targetObject);

		for (ObjectFieldDescriptor fieldDescriptor : new ObjectFieldIterator(sourceObject, targetObject)) {
			// collection

			if (fieldDescriptor.sourceHasReadMethod() && fieldDescriptor.targetHasReadMethod() && List.class.isAssignableFrom(fieldDescriptor.getSourceType()) && List.class.isAssignableFrom(fieldDescriptor.getTargetType())) {
				if (loadAssociations == null || loadAssociations.contains(fieldDescriptor.getFieldName())) {

					List<?> sourceList = (List<?>) fieldDescriptor.getSourceValue();
					@SuppressWarnings("unchecked")
					List<Object> targetList = (List<Object>) fieldDescriptor.getTargetValue();

					for (Object sourceListObject : sourceList) {
						if (sourceListObject != null && sourceListObject.getClass().isEnum()) {
							targetList.add(sourceListObject);
						} else if (visited.containsKey(sourceListObject)) {
							targetList.add(visited.get(sourceListObject));
						} else if (sourceListObject == null) {
							// targetList.add(null);
						} else {
							Class<?> mappedItemType = getMappedTargetType(sourceListObject.getClass());

							if (mappedItemType != null) {
								targetList.add(copyObject(sourceListObject, mappedItemType, visited, classLoadAssociations, attributesToOmit, clearChanges));
							}
						}
					}

					if (targetList instanceof IChangeTracker) {
						((IChangeTracker) targetList).clearChanges();
					}

					// PropertyUtils.setProperty(targetObject,
					// fieldDescriptor.getFieldName(), targetList);
				}

				continue;
			}

			if (fieldDescriptor.sourceHasReadMethod() && fieldDescriptor.targetHasWriteMethod()) {

				if (fieldDescriptor.getSourceValue() == null || attributesToOmit.contains(fieldDescriptor.getFieldName())) {
					continue;
				}

				// source und target typ-mapped stimmen ueberein
				Class<?> mappedType = getMappedTargetType(fieldDescriptor.getTargetType());
				if (fieldDescriptor.getSourceType() == mappedType) {

					if (loadAssociations == null || loadAssociations.contains(fieldDescriptor.getFieldName())) {

						Object object;

						if (visited.containsKey(fieldDescriptor.getSourceValue())) {
							object = visited.get(fieldDescriptor.getSourceValue());
						} else {
							object = copyObject(fieldDescriptor.getSourceValue(), fieldDescriptor.getTargetType(), visited, classLoadAssociations, attributesToOmit, clearChanges);
							visited.put(fieldDescriptor.getSourceValue(), object);
						}

						try {
							PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), object);
						} catch (Exception e) {
							throw new RuntimeException(String.format("error setting property '%s'", fieldDescriptor.getFieldName()), e);
						}
					}
					continue;
				}

				for (IFieldCopyHandler fieldCopyHandler : this.fieldCopyHandlers) {
					if (fieldCopyHandler.check(fieldDescriptor)) {
						try {
							fieldCopyHandler.copy(fieldDescriptor, sourceObject, targetObject);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						break;
					}
				}

			}
		}

		if (clearChanges && targetObject instanceof IHasChangeTracker) {
			((IHasChangeTracker) targetObject).getChangeTracker().clearChanges();
		}

		return targetObject;
	}

	protected Class<?> getMappedTargetType(Class<?> sourceType) {
		return null;
	}

	public Object copyObject(Object sourceObject, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations) {
		return copyObject(sourceObject, getMappedTargetType(sourceObject.getClass()), new HashMap<Object, Object>(), classLoadAssociations, new ArrayList<String>());
	}

}
