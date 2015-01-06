package io.pelle.mango.client.base.util;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;

import java.util.Date;

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

		case YEAR_MONTH_DAY:
			format = new DateTimeFormat("yyyy-MM-dd", info) {
			};
			break;
		}

		return format;
	}

	public static Date parseDate(String dateString, IDateControlModel.DATE_FORMAT dateFormat) {
		try {
			return getFormat(dateFormat).parse(dateString);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public static String formatDate(Date date, IDateControlModel.DATE_FORMAT dateFormat) {
		return getFormat(dateFormat).format(date);
	}

}
