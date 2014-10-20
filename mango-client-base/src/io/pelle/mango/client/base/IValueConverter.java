package io.pelle.mango.client.base;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;

import java.util.Date;

public interface IValueConverter {

	Date parseDate(String dateString);

	String formatDate(Date date, IDateControlModel.DATE_FORMAT datFormat);

}
