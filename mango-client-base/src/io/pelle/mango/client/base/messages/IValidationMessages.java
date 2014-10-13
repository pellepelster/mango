package io.pelle.mango.client.base.messages;

import java.util.Map;

public interface IValidationMessages extends Iterable<IValidationMessage> {
	boolean hasErrors();

	int count();

	String getValidationMessageString(Map<String, Object> context);

	void addValidationMessage(IValidationMessage validationMessage);

}
