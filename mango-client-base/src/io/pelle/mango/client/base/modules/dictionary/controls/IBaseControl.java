package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IBaseDictionaryElement;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;

import com.google.common.base.Optional;

public interface IBaseControl<ValueType, ModelType extends IBaseModel> extends IBaseDictionaryElement<ModelType> {

	public interface IControlUpdateListener {
		void onUpdate();
	}

	void setValue(ValueType value);

	ValueType getValue();

	String format();

	void parseValue(String valueString);

	boolean isMandatory();

	IValidationMessages getValidationMessages();

	void addUpdateListener(IControlUpdateListener controlUpdateListener);

	Optional<IBooleanExpression> getExpression(PathExpression pathExpression);

	void beginEdit();
	
	void endEdit();

}
