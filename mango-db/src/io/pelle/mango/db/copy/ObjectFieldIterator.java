package io.pelle.mango.db.copy;

import io.pelle.mango.db.util.CopyBean;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;

public class ObjectFieldIterator implements Iterable<ObjectFieldDescriptor> {

	private final List<ObjectFieldDescriptor> properties = new ArrayList<ObjectFieldDescriptor>();

	private final List<String> attributesToOmit = new ArrayList<String>();

	public ObjectFieldIterator(Object sourceObject) {
		this(sourceObject, null);
	}

	@SuppressWarnings("unchecked")
	public ObjectFieldIterator(Object sourceObject, Object targetObject) {
		this.attributesToOmit.add("class");

		try {
			for (Map.Entry<String, Object> entry : ((Map<String, Object>) PropertyUtils.describe(sourceObject)).entrySet()) {
				String propertyName = entry.getKey();

				if (this.attributesToOmit.contains(propertyName) || CopyBean.hasAnnotation(sourceObject.getClass(), propertyName, Transient.class)) {
					continue;
				}

				PropertyDescriptor sourcePropertyDescriptor = PropertyUtils.getPropertyDescriptor(sourceObject, propertyName);

				PropertyDescriptor targetPropertyDescriptor = null;
				if (targetObject != null) {
					targetPropertyDescriptor = PropertyUtils.getPropertyDescriptor(targetObject, propertyName);
				}

				Object sourceValue = null;

				if (sourcePropertyDescriptor != null) {
					sourceValue = PropertyUtils.getSimpleProperty(sourceObject, propertyName);
				}

				Object targetValue = null;
				if (targetPropertyDescriptor != null) {
					targetValue = PropertyUtils.getSimpleProperty(targetObject, propertyName);
				}

				ObjectFieldDescriptor fieldDescriptor = new ObjectFieldDescriptor(propertyName, sourcePropertyDescriptor, sourceValue, targetPropertyDescriptor, targetValue);

				this.properties.add(fieldDescriptor);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/** {@inheritDoc} */
	@Override
	public Iterator<ObjectFieldDescriptor> iterator() {
		return this.properties.iterator();
	}

}
