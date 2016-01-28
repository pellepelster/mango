package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IEnumerationControl;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.ObjectExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

public class EnumerationControl<ENUM_TYPE> extends BaseDictionaryControl<IEnumerationControlModel<ENUM_TYPE>, ENUM_TYPE> implements IEnumerationControl<ENUM_TYPE> {

	private Map<String, String> enumerationMap = new HashMap<String, String>();

	public EnumerationControl(IEnumerationControlModel<ENUM_TYPE> controlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(controlModel, parent);

		for (Map.Entry<String, String> enumerationEntry : DictionaryModelProvider.getEnumerationValues(controlModel.getEnumerationName()).entrySet()) {
			enumerationMap.put(enumerationEntry.getKey(), enumerationEntry.getValue());
		}
	}

	@Override
	public String format() {

		ENUM_TYPE value = getValue();

		if (value == null || !enumerationMap.containsKey(value.toString())) {
			return "";
		} else {
			return enumerationMap.get(value.toString());
		}
	}

	@Override
	protected ParseResult parseValueInternal(String valueString) {

		Object enumeration = DictionaryModelProvider.getEnumerationValue(getModel().getEnumerationName(), valueString);

		return new ParseResult((ENUM_TYPE) enumeration);
		// return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR,
		// EnumerationControl.class.getName(),
		// MangoClientWeb.MESSAGES.enumerationParseError(valueString)));
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		if (getValue() != null) {
			return Optional.<IBooleanExpression> of(new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new ObjectExpression((Serializable) getValue())));
		} else {
			return super.getExpression(pathExpression);
		}
	}

	public Map<String, String> getEnumerationMap() {
		return enumerationMap;
	}
}
