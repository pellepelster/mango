package io.pelle.mango.gwt.commons.editableLabel;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;

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
