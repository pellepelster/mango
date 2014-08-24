package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.DictionaryControl;

import java.lang.reflect.Method;

public class ControlQuery {

	public static DictionaryControl getRef(DictionaryControl dictionaryControl) {
		try {
			Method getRefMethod = dictionaryControl.getClass().getMethod("getRef");
			Object result = getRefMethod.invoke(dictionaryControl, new Object[] {});

			return (DictionaryControl) result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
