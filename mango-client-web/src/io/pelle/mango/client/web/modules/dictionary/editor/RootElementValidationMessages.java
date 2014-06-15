package io.pelle.mango.client.web.modules.dictionary.editor;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IBaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.BaseValidationMessages;
import io.pelle.mango.client.web.modules.dictionary.ValidationMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class RootElementValidationMessages extends BaseValidationMessages implements IValidationMessages
{

	private Map<IBaseDictionaryElement<?>, ValidationMessages> validationMessages = new HashMap<IBaseDictionaryElement<?>, ValidationMessages>();

	public RootElementValidationMessages(Map<IBaseDictionaryElement<?>, ValidationMessages> validationMessages)
	{
		super();
		this.validationMessages = validationMessages;
	}

	private List<IValidationMessage> getAllValidationMessages()
	{
		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		for (Map.Entry<IBaseDictionaryElement<?>, ValidationMessages> validationMessageEntry : this.validationMessages.entrySet())
		{
			result.addAll(Lists.newArrayList(validationMessageEntry.getValue().iterator()));
		}

		return result;
	}

	@Override
	public Iterator<IValidationMessage> iterator()
	{
		return getAllValidationMessages().iterator();
	}

	@Override
	public boolean hasErrors()
	{
		Iterator<ValidationMessages> iterator = this.validationMessages.values().iterator();

		while (iterator.hasNext())
		{
			ValidationMessages tmpValidationMessages = iterator.next();

			if (tmpValidationMessages.hasErrors())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int count()
	{
		return getAllValidationMessages().size();
	}

	@Override
	public String getValidationMessageString(Map<String, Object> context)
	{
		String result = "";
		Iterator<ValidationMessages> iterator = this.validationMessages.values().iterator();

		while (iterator.hasNext())
		{
			ValidationMessages tmpValidationMessages = iterator.next();

			if (tmpValidationMessages.hasErrors())
			{
				result += tmpValidationMessages.getValidationMessageString(context);
			}
		}

		return result;
	}

	@Override
	public void addValidationMessage(IValidationMessage validationMessage)
	{
		throw new RuntimeException("not implemented");
	}

}
