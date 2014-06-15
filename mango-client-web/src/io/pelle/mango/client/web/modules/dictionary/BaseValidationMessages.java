package io.pelle.mango.client.web.modules.dictionary;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.IValidationMessages;

import java.util.Iterator;
import java.util.List;

public abstract class BaseValidationMessages implements IValidationMessages
{
	protected IMessage.SEVERITY getSeverity(List<IValidationMessage> validationMessages)
	{
		IMessage.SEVERITY severity = IMessage.SEVERITY.NONE;

		for (IValidationMessage validationMessage : validationMessages)
		{
			if (validationMessage.getSeverity().getOrder() > severity.getOrder())
			{
				severity = validationMessage.getSeverity();
			}
		}

		return severity;
	}

	private boolean hasError(IMessage.SEVERITY severity)
	{
		return severity.getOrder() >= IMessage.SEVERITY.ERROR.getOrder();
	}

	private boolean hasError(IValidationMessage validationMessage)
	{
		return hasError(validationMessage.getSeverity());
	}

	protected boolean hasError(Iterator<IValidationMessage> validationMessageIterator)
	{
		while (validationMessageIterator.hasNext())
		{
			IValidationMessage validationMessage = validationMessageIterator.next();

			if (hasError(validationMessage))
			{
				return true;
			}

		}

		return false;
	}

}
