package io.pelle.mango.client.base.modules.dictionary.model;

import java.util.Map;

public interface IEnumerationConverter {

	Object parseEnum(String enumerationName, String enumerationValue);

	Map<String, String> getEnumValues(String enumerationName);

}
