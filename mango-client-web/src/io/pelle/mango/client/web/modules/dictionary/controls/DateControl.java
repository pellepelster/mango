package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.MangoClientBase;
import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IDateControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.DateValueExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import java.util.Date;

import com.google.common.base.Optional;

public class DateControl extends BaseDictionaryControl<IDateControlModel, Date> implements IDateControl {

	public DateControl(IDateControlModel dateControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(dateControlModel, parent);
	}

	@Override
	public String format() {
		if (getValue() != null && getValue() instanceof Date) {

			return MangoClientBase.getInstance().getValueConverter().formatDate(getValue(), IDateControlModel.DATE_FORMAT.MEDIUM);
		} else {
			return super.format();
		}
	}

	@Override
	protected ParseResult parseValueInternal(String valueString) {
		try {
			return new ParseResult(MangoClientBase.getInstance().getValueConverter().parseDate(valueString));
		} catch (Exception e) {
			return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, DateControl.class.getName(), MangoClientWeb.MESSAGES.dateParseError(valueString)));
		}
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		if (getValue() != null) {
			return Optional.<IBooleanExpression> of(new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new DateValueExpression(getValue())));
		} else {
			return super.getExpression(pathExpression);
		}
	}

}
