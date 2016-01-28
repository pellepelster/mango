package io.pelle.mango.db.copy;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;

import io.pelle.mango.db.util.CopyBean;

public class ObjectFieldIterator implements Iterable<ObjectFieldDescriptor> {

	private final List<ObjectFieldDescriptor> properties = new ArrayList<ObjectFieldDescriptor>();

	private final List<String> attributesToOmit = new ArrayList<String>();

	public ObjectFieldIterator(Object sourceObject) {
		this(sourceObject, null);
	}

	public ObjectFieldIterator(Object sourceObject, Object targetObject) {

		this.attributesToOmit.add("class");

		try {

			for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(sourceObject.getClass())) {

				String propertyName = pd.getName();

				if (this.attributesToOmit.contains(propertyName) || CopyBean.hasAnnotation(sourceObject.getClass(), propertyName, Transient.class)) {
					continue;
				}

				PropertyDescriptor sourcePropertyDescriptor = PropertyUtils.getPropertyDescriptor(sourceObject, propertyName);

				PropertyDescriptor targetPropertyDescriptor = null;
				if (targetObject != null) {
					targetPropertyDescriptor = PropertyUtils.getPropertyDescriptor(targetObject, propertyName);
				}

				ObjectFieldDescriptor fieldDescriptor = new ObjectFieldDescriptor(propertyName, sourcePropertyDescriptor, targetPropertyDescriptor);

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
