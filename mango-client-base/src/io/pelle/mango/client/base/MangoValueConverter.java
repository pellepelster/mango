package io.pelle.mango.client.base;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class MangoValueConverter implements IValueConverter {

	@Override
	public Date parseDate(String dateString) {

		String longDateString = parseDateNative(dateString);
		long longDate = Long.parseLong(longDateString);

		return new Date(longDate);
	}

	public native String parseDateNative(String dateString) /*-{
															var longDate = Date.parse(dateString);
															return longDate.toString();
															}-*/;

	@Override
	public String formatDate(Date date, IDateControlModel.DATE_FORMAT format) {

		DateTimeFormat format1 = null;

		switch (format) {
		case MEDIUM:
			format1 = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
			break;

		case MONTH_DAY_YEAR:
			format1 = DateTimeFormat.getFormat("MM/dd/yyyy");
			break;
		}
		return format1.format(date);
	}

}
