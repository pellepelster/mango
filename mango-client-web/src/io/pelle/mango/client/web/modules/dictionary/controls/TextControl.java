package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.ITextControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ITextControlModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class TextControl extends BaseDictionaryControl<ITextControlModel, String> implements ITextControl
{

	public TextControl(ITextControlModel textControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(textControlModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		return new ParseResult(valueString);
	}

}
