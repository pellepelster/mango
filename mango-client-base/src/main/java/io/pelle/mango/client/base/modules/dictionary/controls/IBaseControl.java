package io.pelle.mango.client.base.modules.dictionary.controls;

import com.google.common.base.Optional;

import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IBaseDictionaryElement;
import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;

public interface IBaseControl<ValueType, ModelType extends IBaseControlModel> extends IBaseDictionaryElement<ModelType> {

	void setValue(ValueType value);

	ValueType getValue();

	String format();

	void parseValue(String valueString);

	boolean isMandatory();

	boolean isReadonly();

	IValidationMessages getValidationMessages();

	void addUpdateListener(IUpdateListener controlUpdateListener);

	Optional<IBooleanExpression> getExpression(PathExpression pathExpression);

	void beginEdit();

	void endEdit();

}
