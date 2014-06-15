package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IBooleanControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class BooleanControl extends BaseDictionaryControl<IBooleanControlModel, Boolean>  implements IBooleanControl
{

	public BooleanControl(IBooleanControlModel booleanControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(booleanControlModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		return new ParseResult(Boolean.parseBoolean(valueString));
	}
}
