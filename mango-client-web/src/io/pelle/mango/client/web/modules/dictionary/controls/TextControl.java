package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.ITextControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ITextControlModel;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import com.google.common.base.Optional;

public class TextControl extends BaseDictionaryControl<ITextControlModel, String> implements ITextControl {

	public TextControl(ITextControlModel textControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(textControlModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString) {
		return new ParseResult(valueString);
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		if (getValue() != null && !getValue().trim().isEmpty()) {
			return Optional.<IBooleanExpression> of(new CompareExpression(pathExpression, ComparisonOperator.LIKE_NO_CASE, new StringExpression(getValue() + "%")));
		} else {
			return super.getExpression(pathExpression);
		}
	}

}
