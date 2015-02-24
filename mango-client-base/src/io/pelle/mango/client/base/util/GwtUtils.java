package io.pelle.mango.client.base.util;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;

import java.text.DateFormat;
import java.util.Date;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;

public class GwtUtils {

	public static DateTimeFormat getFormat(IDateControlModel.DATE_FORMAT dateFormat) {

		DateTimeFormat format = null;
		DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();

		switch (dateFormat) {
		case DATE_TIME_SHORT:
			format = new DateTimeFormat(info.dateTimeShort(info.timeFormatShort(), info.dateFormatShort()), info) {
			};
			break;
		case DATE_SHORT:
			format = new DateTimeFormat(info.dateFormatShort()) {
			};
			break;
		case YEAR_MONTH_DAY_HOUR_MINUTE_SECOND:
			format = new DateTimeFormat("yyyy-MM-dd HH:mm:ss", info) {
			};
			break;
		}

		return format;
	}

	public static Date parseDate(String dateString, IDateControlModel.DATE_FORMAT dateFormat) {

		Date date = null;
		try {

			if (GWT.isClient()) {
				date = getFormat(dateFormat).parse(dateString);
			} else {
				switch (dateFormat) {
				case DATE_TIME_SHORT:
					DateFormat dateTimeShort = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
					date = dateTimeShort.parse(dateString);
				case DATE_SHORT:
					DateFormat dateShort = DateFormat.getDateInstance(DateFormat.SHORT);
					date = dateShort.parse(dateString);
				}
			}
		} catch (Exception e) {
			e.toString();
		}

		return date;
	}

	public static String formatDate(Date date, IDateControlModel.DATE_FORMAT dateFormat) {
		if (GWT.isClient()) {
			return getFormat(dateFormat).format(date);
		} else {
			DateFormat dateFormatInstance = null;
			switch (dateFormat) {
			case DATE_TIME_SHORT:
				dateFormatInstance = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
			case DATE_SHORT:
				dateFormatInstance = DateFormat.getDateInstance(DateFormat.SHORT);
			}
			return dateFormatInstance.format(date);
		}
	}

}
