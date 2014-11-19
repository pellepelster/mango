package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IBigDecimalControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.NumberExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import java.math.BigDecimal;

import com.google.common.base.Optional;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.NumberFormat;

public class BigDecimalControl extends BaseDictionaryControl<IBigDecimalControlModel, BigDecimal> implements IBigDecimalControl {

	public BigDecimalControl(IBigDecimalControlModel decimalControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(decimalControlModel, parent);
	}

	@Override
	public String format() {
		if (getValue() != null && getValue() instanceof BigDecimal) {
			if (GWT.isClient()) {
				return NumberFormat.getDecimalFormat().format(getValue());
			} else {
				return getValue().toString();
			}
		} else {
			return super.format();
		}
	}

	@Override
	protected ParseResult parseValueInternal(String valueString) {
		try {
			return new ParseResult(new BigDecimal(valueString));
		} catch (NumberFormatException e) {
			return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, BigDecimalControl.class.getName(), MangoClientWeb.MESSAGES.decimalParseError(valueString)));
		}
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		if (getValue() != null) {
			return Optional.<IBooleanExpression> of(new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new NumberExpression(getValue())));
		} else {
			return super.getExpression(pathExpression);
		}
	}

}
