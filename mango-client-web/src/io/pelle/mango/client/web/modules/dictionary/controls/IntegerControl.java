package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IIntegerControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class IntegerControl extends BaseDictionaryControl<IIntegerControlModel, Integer>  implements IIntegerControl
{

	public IntegerControl(IIntegerControlModel integerControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(integerControlModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		try
		{
			return new ParseResult(new Integer(valueString));
		}
		catch (NumberFormatException e)
		{
			return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, IntegerControl.class.getName(),
					MangoClientWeb.MESSAGES.integerParseError(valueString)));
		}
	}

}
