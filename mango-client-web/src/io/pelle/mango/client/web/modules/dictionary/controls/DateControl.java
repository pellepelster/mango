package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IDateControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;
import io.pelle.mango.client.base.util.GwtUtils;
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

	private String valueString;

	public DateControl(IDateControlModel dateControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(dateControlModel, parent);
	}

	@Override
	public String format() {
		if (getValue() != null && getValue() instanceof Date) {
			return GwtUtils.formatDate(getValue(), getModel().getDateFormat());
		} else {
			return super.format();
		}
	}

	@Override
	public void parseValue(String valueString) {
		this.valueString = valueString;
		setValueInternal(null);
	}

	@Override
	protected BaseDictionaryControl<IDateControlModel, Date>.ParseResult parseValueInternal(String valueString) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void endEdit() {

		if (valueString != null && !valueString.trim().isEmpty()) {

			Date parsedDate = GwtUtils.parseDate(valueString, getModel().getDateFormat());

			if (parsedDate != null) {
				setValue(parsedDate);
			} else {
				addValidationMessage(new ValidationMessage(IMessage.SEVERITY.ERROR, DateControl.class.getName(), MangoClientWeb.MESSAGES.dateParseError(valueString)));
			}
		} else {
			setValue(null);
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
