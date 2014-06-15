package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IBaseDictionaryElement;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

public interface IBaseControl<ValueType, ModelType extends IBaseModel> extends IBaseDictionaryElement<ModelType>
{
	public interface IControlUpdateListener
	{
		void onUpdate();
	}

	void setValue(ValueType value);

	ValueType getValue();

	String format();

	void parseValue(String valueString);

	boolean isMandatory();

	IValidationMessages getValidationMessages();

	void addUpdateListener(IControlUpdateListener controlUpdateListener);

}
