package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IIntegerControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.LongExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import com.google.common.base.Optional;

public class IntegerControl extends BaseDictionaryControl<IIntegerControlModel, Integer>  implements IIntegerControl
{

	public IntegerControl(IIntegerControlModel integerControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(integerControlModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		try
		{
			return new ParseResult(new Integer(valueString));
		}
		catch (NumberFormatException e)
		{
			return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, IntegerControl.class.getName(),
					MangoClientWeb.MESSAGES.integerParseError(valueString)));
		}
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		if (getValue() != null) {
			return Optional.<IBooleanExpression> of(new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new LongExpression(getValue())));
		} else {
			return super.getExpression(pathExpression);
		}
	}
}
