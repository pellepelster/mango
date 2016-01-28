package io.pelle.mango.client.gwt.modules.dictionary.controls.time;

import com.google.gwt.user.cellview.client.Column;

public abstract class TimestampColumn<T> extends Column<T, Long> {

	public TimestampColumn() {
		super(new TimestampCell());
	}
}