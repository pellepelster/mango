package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IEnumerationControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

import java.util.HashMap;
import java.util.Map;

public class EnumerationControlModel<ENUM_TYPE> extends BaseControlModel<IEnumerationControl<ENUM_TYPE>> implements IEnumerationControlModel<ENUM_TYPE> {

	private static final long serialVersionUID = -3831710976796569500L;

	private Map<String, String> enumerationMap = new HashMap<String, String>();

	private String enumerationName;

	public EnumerationControlModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public Map<String, String> getEnumerationMap() {
		return this.enumerationMap;
	}

	@Override
	public String getEnumerationName() {
		return enumerationName;
	}

	public void setEnumerationName(String enumerationName) {
		this.enumerationName = enumerationName;
	}

}
