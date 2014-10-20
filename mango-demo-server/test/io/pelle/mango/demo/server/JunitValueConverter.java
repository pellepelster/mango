package io.pelle.mango.demo.server;

import io.pelle.mango.client.base.IValueConverter;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JunitValueConverter implements IValueConverter {

	private DateFormat dateFormat;

	public JunitValueConverter() {
		super();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
	}

	@Override
	public Date parseDate(String dateString) {
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String formatDate(Date date, IDateControlModel.DATE_FORMAT format) {
		return dateFormat.format(date);
	}
}
