package io.pelle.mango.gwt.commons;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBox;

public class StringEditableLabel extends BaseEditableLabel<String, TextBox> {

	public StringEditableLabel(ValueChangeHandler<String> handler) {
		super(handler);
	}

	@Override
	protected String formatValue(String value) {
		return value;
	}

	protected TextBox createTextBox() {
		return new TextBox();
	}

	@Override
	protected TextBox createControl() {
		return new TextBox();
	}

	@Override
	protected String getValueFromControl() {
		return getControl().getValue();
	}

	@Override
	protected void setValueToControl(String value) {
		getControl().setValue(value);
	}

}
