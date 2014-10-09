package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IBooleanControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.BooleanValueExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import com.google.common.base.Optional;

public class BooleanControl extends BaseDictionaryControl<IBooleanControlModel, Boolean> implements IBooleanControl {

	public BooleanControl(IBooleanControlModel booleanControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(booleanControlModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString) {
		return new ParseResult(Boolean.parseBoolean(valueString));
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		if (getValue() != null) {
			return Optional.<IBooleanExpression> of(new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new BooleanValueExpression(getValue())));
		} else {
			return super.getExpression(pathExpression);
		}
	}
}
