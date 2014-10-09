package io.pelle.mango.client.web.test.sync.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IEnumerationControl;

import java.util.Collections;
import java.util.Map;

public class EnumerationTestControl<ENUM_TYPE> extends BaseControlTest<IEnumerationControl<ENUM_TYPE>, ENUM_TYPE> {

	private Map<String, String> enumerationMap = Collections.emptyMap();

	public EnumerationTestControl(IEnumerationControl<ENUM_TYPE> control) {
		super(control);
		onUpdate();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.enumerationMap = getControl().getEnumerationMap();

	}

	public Map<String, String> getEnumerationMap() {
		return enumerationMap;
	}

}
