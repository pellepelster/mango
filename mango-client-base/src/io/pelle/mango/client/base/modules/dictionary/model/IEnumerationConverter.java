package io.pelle.mango.client.base.modules.dictionary.model;


public interface IEnumerationConverter {

	Object parseEnum(String enumerationName, String enumerationValue);

	String[] getEnumValues(String enumerationName);

}
