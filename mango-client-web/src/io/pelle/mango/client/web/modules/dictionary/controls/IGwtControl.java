package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IValidationMessages;

public interface IGwtControl
{
	void setContent(Object content);
	
	void showMessages(IValidationMessages validationMessages);
}
