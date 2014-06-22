package io.pelle.mango.client.web.test.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl.IControlUpdateListener;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class BaseControlTest<ElementType extends IBaseControl<ValueType, ?>, ValueType> implements IControlUpdateListener {
	private ValueType value;

	private ElementType baseControl;

	private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

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
		Assert.assertFalse(this.validationMessages.isEmpty());
	}

	public void assertHasNoErrors() {
		Assert.assertTrue(this.validationMessages.isEmpty());
	}

	public void assertHasErrorWithText(String text) {

		for (IValidationMessage validationMessage : this.validationMessages) {
			if (validationMessage.getMessage().equals(text)) {
				return;
			}
		}

		Assert.fail("message with containing '" + text + "' not found (messages: " + Joiner.on(", ").join(validationMessages) + ")");
	}

	@Override
	public void onUpdate() {
		this.value = this.baseControl.getValue();
		this.validationMessages = Lists.newArrayList(getBaseControl().getValidationMessages());
	}

}
