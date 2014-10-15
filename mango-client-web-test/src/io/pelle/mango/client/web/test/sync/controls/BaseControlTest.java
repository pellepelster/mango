package io.pelle.mango.client.web.test.sync.controls;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl.IControlUpdateListener;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class BaseControlTest<ElementType extends IBaseControl<ValueType, ?>, ValueType> implements IControlUpdateListener {

	private ValueType value;

	private ElementType baseControl;

	private boolean hasErrors;

	private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

	private String valueString;

	public BaseControlTest(ElementType baseControl) {
		this.baseControl = baseControl;

		baseControl.addUpdateListener(this);
		onUpdate();
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

	public void parseValue(String valueString) {
		beginEdit();
		this.baseControl.parseValue(valueString);
		endEdit();
	}

	public void beginEdit() {
		this.baseControl.beginEdit();
	}

	public void endEdit() {
		this.baseControl.endEdit();
	}

	public void assertMandatory() {
		Assert.assertTrue(getBaseControl().isMandatory());
	}

	public void assertHasErrors() {
		Assert.assertTrue(hasErrors);
	}

	public void assertHasNoErrors() {
		Assert.assertFalse(hasErrors);
	}

	public void assertValueString(String valueString) {
		Assert.assertEquals(valueString, this.valueString);
	}
	
	public void assertHasErrorWithText(String text) {

		for (IValidationMessage validationMessage : validationMessages) {
			if (validationMessage.getHumanMessage().equals(text)) {
				return;
			}
		}

		String message = "message containing '" + text + "' not found (messages: " + Joiner.on(", ").join(baseControl.getValidationMessages()) + ")";
		Assert.fail(message);
	}

	protected ElementType getControl() {
		return baseControl;
	}

	@Override
	public void onUpdate() {
		this.value = this.baseControl.getValue();
		this.hasErrors = baseControl.getValidationMessages().hasErrors();
		this.validationMessages.clear();
		this.validationMessages.addAll(Lists.newArrayList(baseControl.getValidationMessages()));
		this.valueString = baseControl.format();
	}
}
