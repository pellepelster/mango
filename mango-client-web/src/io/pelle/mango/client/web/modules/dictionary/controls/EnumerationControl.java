package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IEnumerationControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import java.util.Map;

public class EnumerationControl extends BaseDictionaryControl<IEnumerationControlModel, Object> implements IEnumerationControl
{

	public EnumerationControl(IEnumerationControlModel enumerationControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(enumerationControlModel, parent);
	}

	@Override
	public String format()
	{
		if (getValue() == null)
		{
			return "";
		}
		else
		{
			return getModel().getEnumeration().get(getValue().toString()).toString();
		}
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		for (Map.Entry<String, String> enumEntry : getModel().getEnumeration().entrySet())
		{
			if (enumEntry.getValue().equals(valueString))
			{
				return new ParseResult(enumEntry.getKey());
			}
		}

		return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, EnumerationControl.class.getName(),
				MangoClientWeb.MESSAGES.enumerationParseError(valueString)));
	}

}
