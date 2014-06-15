package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IDateControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class DateControl extends BaseDictionaryControl<IDateControlModel, Date> implements IDateControl
{

	public DateControl(IDateControlModel dateControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(dateControlModel, parent);
	}

	@Override
	public String format()
	{
		if (getValue() != null && getValue() instanceof Date)
		{
			DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
			return dateFormat.format(getValue());
		}
		else
		{
			return super.format();
		}
	}

	public native String parseDateNative(String dateString) /*-{
															var longDate = Date.parse(dateString);
															return longDate.toString();
															}-*/;

	public Date parseDate(String dateString)
	{
		String longDateString = parseDateNative(dateString);
		long longDate = Long.parseLong(longDateString);

		return new Date(longDate);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		try
		{
			return new ParseResult(parseDate(valueString));
		}
		catch (Exception e)
		{
			return new ParseResult(new ValidationMessage(IMessage.SEVERITY.ERROR, DateControl.class.getName(), MangoClientWeb.MESSAGES.dateParseError(valueString)));
		}
	}

}
