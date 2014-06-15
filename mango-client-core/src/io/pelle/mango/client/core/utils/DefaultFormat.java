package io.pelle.mango.client.core.utils;

import java.util.Date;

import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;

public class DefaultFormat implements Format
{

	/**
	 * Default style name added when the date box has a format error.
	 */
	private static final String DATE_BOX_FORMAT_ERROR = "dateBoxFormatError";

	private final com.google.gwt.i18n.shared.DateTimeFormat dateTimeFormat;

	/**
	 * Creates a new default format instance.
	 */
	@SuppressWarnings("deprecation")
	public DefaultFormat()
	{
		this.dateTimeFormat = DateTimeFormat.getMediumDateTimeFormat();
	}

	/**
	 * Creates a new default format instance.
	 * 
	 * @param dateTimeFormat
	 *            the {@link DateTimeFormat} to use with this {@link Format}.
	 */
	public DefaultFormat(com.google.gwt.i18n.shared.DateTimeFormat dateTimeFormat)
	{
		this.dateTimeFormat = dateTimeFormat;
	}

	@Override
	public String format(DateBox box, Date date)
	{
		if (date == null)
		{
			return "";
		}
		else
		{
			return this.dateTimeFormat.format(date);
		}
	}

	/**
	 * Gets the date time format.
	 * 
	 * @return the date time format
	 */
	public com.google.gwt.i18n.shared.DateTimeFormat getDateTimeFormat()
	{
		return this.dateTimeFormat;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Date parse(DateBox dateBox, String dateText, boolean reportError)
	{
		Date date = null;
		try
		{
			if (dateText.length() > 0)
			{
				date = this.dateTimeFormat.parse(dateText);
			}
		}
		catch (IllegalArgumentException exception)
		{
			try
			{
				date = new Date(dateText);
			}
			catch (IllegalArgumentException e)
			{
				if (reportError)
				{
					dateBox.addStyleName(DATE_BOX_FORMAT_ERROR);
				}
				return null;
			}
		}
		return date;
	}

	@Override
	public void reset(DateBox dateBox, boolean abandon)
	{
		dateBox.removeStyleName(DATE_BOX_FORMAT_ERROR);
	}
}