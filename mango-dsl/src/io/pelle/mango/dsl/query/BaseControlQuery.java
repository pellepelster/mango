package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.mango.BaseDictionaryControl;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryControl;

import java.lang.reflect.Method;

public class BaseControlQuery extends BaseEObjectQuery<BaseDictionaryControl> {

	private BaseControlQuery(BaseDictionaryControl baseDictionaryControl) {
		super(baseDictionaryControl);
	}

	public static DictionaryControl getRef(DictionaryControl dictionaryControl) {
		try {
			Method getRefMethod = dictionaryControl.getClass().getMethod("getRef");
			Object result = getRefMethod.invoke(dictionaryControl, new Object[] {});

			return (DictionaryControl) result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static BaseControlQuery createQuery(BaseDictionaryControl baseDictionaryControl) {
		return new BaseControlQuery(baseDictionaryControl);
	}

	public DictionaryQuery getParentDictionary() {
		return DictionaryQuery.createQuery(ModelUtil.getParentWithType(getObject(), Dictionary.class));
	}

}
