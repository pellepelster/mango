package io.pelle.mango.gwt.commons;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;

public class BooleanEditableLabel extends BaseEditableLabel<Boolean, CheckBox> {

	public BooleanEditableLabel(ValueChangeHandler<Boolean> handler) {
		super(handler);
	}

	public BooleanEditableLabel() {
	}

	@Override
	protected String formatValue(Boolean value) {
		return value.toString();
	}

	protected TextBox createTextBox() {
		return new TextBox();
	}

	@Override
	protected CheckBox createControl() {
		return new CheckBox();
	}

	@Override
	protected Boolean getValueFromControl() {
		return getControl().getValue();
	}

	@Override
	protected void setValueToControl(Boolean value) {
		getControl().setValue(value);
	}

}
