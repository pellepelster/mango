/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.base.modules.dictionary.model;

import io.pelle.mango.client.base.vo.IEntityDescriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class VOMetaModelProvider {

	private static Map<String, IEntityDescriptor<?>> descriptors = new HashMap<String, IEntityDescriptor<?>>();

	public static IEntityDescriptor<?> getValueObjectEntityDescriptor(String className) {
		if (descriptors.containsKey(className)) {
			return descriptors.get(className);
		} else {
			throw new RuntimeException("class '" + className + "' not found");
		}
	}

	public static void registerEntityDescriptor(IEntityDescriptor<?> entityDescriptor) {
		descriptors.put(entityDescriptor.getVOEntityClass().getName(), entityDescriptor);
	}

	public static IEntityDescriptor<?> getEntityDescriptor(IDictionaryModel dictionaryModel) {
		return getValueObjectEntityDescriptor(dictionaryModel.getVOClass().getName());
	}

	public static Collection<IEntityDescriptor<?>> getEntityDescriptors() {
		return descriptors.values();
	}

}
