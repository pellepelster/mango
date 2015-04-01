package io.pelle.mango.gwt.commons.editableLabel;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBox;

public class StringEditableLabel extends BaseEditableLabel<String, TextBox> {

	public StringEditableLabel(ValueChangeHandler<String> handler) {
		super(handler);
	}

	public StringEditableLabel() {
	}

	@Override
	protected String formatValue(String value) {
		return value;
	}

	@Override
	protected TextBox createControl() {
		TextBox textBox = new TextBox();
		textBox.setWidth("14em");
		return textBox;
	}

	@Override
	protected String getValueFromControl() {
		if (getControl().getValue() == null || getControl().getValue().trim().isEmpty()) {
			return null;
		} else {
			return getControl().getValue();
		}
	}

	@Override
	protected void setValueToControl(String value) {
		getControl().setValue(value);
	}

}
