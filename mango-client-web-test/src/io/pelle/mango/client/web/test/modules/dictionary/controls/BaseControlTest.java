package io.pelle.mango.client.web.test.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl.IControlUpdateListener;
import junit.framework.Assert;

import com.google.common.base.Joiner;
import com.google.gwt.core.shared.GWT;

public class BaseControlTest<ElementType extends IBaseControl<ValueType, ?>, ValueType> implements IControlUpdateListener {
	private ValueType value;

	private ElementType baseControl;

	public BaseControlTest(ElementType baseControl) {
		this.baseControl = baseControl;

		baseControl.addUpdateListener(this);
	}

	public void assertValue(ValueType expectedValue) {
		Assert.assertEquals(expectedValue, this.baseControl.getValue());
	}

	public ValueType getValue() {
		return this.value;
	}

	protected ElementType getBaseControl() {
		return this.baseControl;
	}

	public void setValue(ValueType value) {
		this.baseControl.setValue(value);
	}

	public void parse(String valueString) {
		this.baseControl.parseValue(valueString);
	}

	public void assertMandatory() {
		Assert.assertTrue(getBaseControl().isMandatory());
	}

	public void assertHasErrors() {
		Assert.assertTrue(this.baseControl.getValidationMessages().hasErrors());
	}

	public void assertHasNoErrors() {
		Assert.assertFalse(this.baseControl.getValidationMessages().hasErrors());
	}

	public void assertHasErrorWithText(String text) {

		for (IValidationMessage validationMessage : baseControl.getValidationMessages()) {
			if (validationMessage.getHumanMessage().equals(text)) {
				return;
			}
		}

		String message = "message containing '" + text + "' not found (messages: " + Joiner.on(", ").join(baseControl.getValidationMessages()) + ")";
		GWT.log(message);
		Assert.fail(message);
	}

	@Override
	public void onUpdate() {
		this.value = this.baseControl.getValue();
	}

}
