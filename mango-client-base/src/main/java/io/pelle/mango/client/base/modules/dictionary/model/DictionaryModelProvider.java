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

import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryModelProvider {

	private static Map<String, IDictionaryModel> dictionaries = new HashMap<String, IDictionaryModel>();

	private static List<IEnumerationConverter> enumerationConverters = new ArrayList<IEnumerationConverter>();

	private static Logger LOG = Logger.getLogger(DictionaryModelProvider.class.getName());

	public static IDictionaryModel getDictionary(String dictionaryName) {

		if (dictionaries.isEmpty()) {
			LOG.log(Level.SEVERE, "no dictionary models found, maybe you forgot to register client/dictionary configuration"); //$NON-NLS-1$
		}

		if (dictionaries.containsKey(dictionaryName)) {
			return dictionaries.get(dictionaryName);
		} else {
			throw new RuntimeException("dictionary '" + dictionaryName + "' not found");
		}
	}

	public static List<IDictionaryModel> getDictionaries(List<String> dictionaryNames) {
		List<IDictionaryModel> dictionaryModels = new ArrayList<IDictionaryModel>();

		for (String dictionaryName : dictionaryNames) {
			dictionaryModels.add(getDictionary(dictionaryName));
		}

		return dictionaryModels;
	}

	public static IDictionaryModel getDictionaryModelForClass(Class<? extends IBaseVO> voClass) {
		for (Map.Entry<String, IDictionaryModel> entry : dictionaries.entrySet()) {
			if (entry.getValue().getVOClass().equals(voClass)) {
				return entry.getValue();
			}
		}

		throw new RuntimeException("no dictionary model found for vo class '" + voClass.getName() + "'");
	}

	public static void registerDictionary(IDictionaryModel dictionary) {
		dictionaries.put(dictionary.getName(), dictionary);
	}

	public static void registerEnumerationConverter(IEnumerationConverter enumerationConverter) {
		enumerationConverters.add(enumerationConverter);
	}

	public static Object getEnumerationValue(String enumerationName, String enumerationValue) {

		if (enumerationValue == null || enumerationValue.trim().isEmpty()) {
			return null;
		}

		for (IEnumerationConverter enumerationConverter : enumerationConverters) {

			Object result = enumerationConverter.parseEnum(enumerationName, enumerationValue);

			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public static Map<String, String> getEnumerationValues(String enumerationName) {

		for (IEnumerationConverter enumerationConverter : enumerationConverters) {

			Map<String, String> result = enumerationConverter.getEnumValues(enumerationName);

			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public static Collection<IDictionaryModel> getAllDictionaries() {
		return dictionaries.values();
	}

}
