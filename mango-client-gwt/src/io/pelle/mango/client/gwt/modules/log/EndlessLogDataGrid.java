package io.pelle.mango.client.gwt.modules.log;

import io.pelle.mango.client.gwt.modules.dictionary.controls.time.TimestampColumn;
import io.pelle.mango.client.log.LogEntryVO;
import io.pelle.mango.client.web.MangoClientWeb;

import java.io.Serializable;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextColumn;

public class EndlessLogDataGrid extends EndlessDataGrid<LogEntryVO> {

	public static final String CSS_LEVEL_STYLE_PREFIX = "log-level-";

	public static final Column<LogEntryVO, ?> LOG_TIMESTAMP_COLUMN = new TimestampColumn<LogEntryVO>() {
		@Override
		public Long getValue(LogEntryVO object) {
			return object.getTimestamp();
		}
	};

	public static final Column<LogEntryVO, ?> LOG_MESSAGE_COLUMN = new TextColumn<LogEntryVO>() {

		@Override
		public String getValue(LogEntryVO object) {
			return object.getMessage();
		}
	};

	public EndlessLogDataGrid() {
		this(null);
	}

	public void setReferencee(Serializable reference) {
		setDataGridCallback(LogDataGridCallback.create(reference));
	}

	public EndlessLogDataGrid(Serializable reference) {
		super();
		setDataGridCallback(LogDataGridCallback.create(reference));

		addRowStyles(new RowStyles<LogEntryVO>() {

			@Override
			public String getStyleNames(LogEntryVO row, int rowIndex) {
				return CSS_LEVEL_STYLE_PREFIX + row.getLevel().toString();
			}
		});

		addColumn(LOG_TIMESTAMP_COLUMN, MangoClientWeb.MESSAGES.logTimestampTitle());
		addColumn(LOG_MESSAGE_COLUMN, MangoClientWeb.MESSAGES.logMessageTitle());

	}

}
