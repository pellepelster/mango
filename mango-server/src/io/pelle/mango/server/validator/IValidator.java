package io.pelle.mango.server.validator;

import io.pelle.mango.client.base.messages.IValidationMessage;

import java.util.List;

public interface IValidator
{
	boolean canValidate(Object o);

	List<IValidationMessage> validate(Object o);
}