package io.pelle.mango.client.gwt.modules.dictionary.controls.time;

import java.util.Date;

import com.google.gwt.user.cellview.client.Column;

public abstract class DateColumn<T> extends Column<T, Date> {

	public DateColumn() {
		super(new DateCell());
	}
}