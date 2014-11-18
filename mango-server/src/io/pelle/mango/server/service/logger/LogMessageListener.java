package io.pelle.mango.server.service.logger;

import io.pelle.mango.server.log.LogEntry;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class LogMessageListener {

	private static final Logger LOG = Logger.getLogger(LogMessageListener.class);

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert logMessageInsert;

	private String messageTextColumnName;

	@Autowired
	public void onLogEvent(LogEvent logEvent) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put(messageTextColumnName, logEvent.getMessage());

		// logMessageInsert.execute(model);

	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.logMessageInsert = new SimpleJdbcInsert(dataSource).withTableName(getLogTableName());
		messageTextColumnName = getColumnName(LogEntry.MESSAGE.getAttributeName());
	}

	private String getColumnName(String fieldName) {
		Column column;
		try {
			column = LogEntry.class.getDeclaredField(fieldName).getAnnotation(Column.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return column.name();
	}

	private String getLogTableName() {
		Table table = LogEntry.class.getAnnotation(Table.class);
		return table.name();
	}

	@Autowired
	public void setLogEventBus(LogEventBus logEventBus) {
		logEventBus.register(this);
	}

}