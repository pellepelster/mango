package io.pelle.mango.gwt.commons.editableLabel;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBox;

public class IntegerEditableLabel extends BaseEditableLabel<Integer, TextBox> {

	public IntegerEditableLabel(ValueChangeHandler<Integer> handler) {
		super(handler);
	}

	public IntegerEditableLabel() {
	}

	@Override
	protected String formatValue(Integer value) {
		return value.toString();
	}

	@Override
	protected TextBox createControl() {
		TextBox textBox = new TextBox();
		textBox.setWidth("5em");
		return textBox;
	}

	@Override
	protected Integer getValueFromControl() {
		if (getControl().getValue() == null || getControl().getValue().trim().isEmpty()) {
			return null;
		} else {
			return Integer.parseInt(getControl().getValue());
		}
	}

	@Override
	protected void setValueToControl(Integer value) {
		if (value == null) {
			getControl().setValue("");
		} else {
			getControl().setValue(value.toString());
		}

	}

	@Override
	protected boolean validateControl() {
		if (getControl().getValue() == null || getControl().getValue().trim().isEmpty()) {
			return true;
		} else {
			try {
				Integer.parseInt(getControl().getValue());
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}
}
