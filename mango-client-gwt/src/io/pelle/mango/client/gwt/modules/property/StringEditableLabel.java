package io.pelle.mango.client.gwt.modules.property;

import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class StringEditableLabel extends BaseEditableLabel<String> {

	public StringEditableLabel(ValueChangeHandler<String> handler) {
		super(handler);
	}

	@Override
	protected String parseValue(String valueString) {
		if (valueString != null && valueString.trim().isEmpty()) {
			return null;
		} else {
			return valueString;
		}
	}

	@Override
	protected String valueToString(String value) {
		return value;
	}

}
