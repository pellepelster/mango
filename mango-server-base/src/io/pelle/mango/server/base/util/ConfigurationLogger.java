package io.pelle.mango.server.base.util;

import io.pelle.mango.server.base.ConfigurationParameters;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.CellStyle.AbbreviationStyle;
import org.nocrala.tools.texttablefmt.CellStyle.HorizontalAlign;
import org.nocrala.tools.texttablefmt.CellStyle.NullStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ConfigurationLogger implements ApplicationListener<ContextRefreshedEvent> {

	private final AbstractBeanFactory beanFactory;

	private final Map<String, String> cache = new ConcurrentHashMap<>();

	private boolean logged = false;

	@Autowired
	protected ConfigurationLogger(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public String getProperty(String key) {

		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		String value = null;
		try {
			value = beanFactory.resolveEmbeddedValue("${" + key.trim() + "}");

			if (value != null) {
				cache.put(key, value);
			}
		} catch (IllegalArgumentException e) {
			// ignore non existant values
		}

		if (value != null) {
			cache.put(key, value);
		} else {
			value = "<none>";
		}

		return value;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (!logged) {

			CellStyle cs = new CellStyle(HorizontalAlign.center, AbbreviationStyle.crop, NullStyle.emptyString);
			Table table = new Table(3, BorderStyle.CLASSIC, ShownBorders.ALL, false, "");
			table.addCell("Properties", cs, 3);
			table.addCell("property");
			table.addCell("value");
			table.addCell("default");

			addProperty(table, ConfigurationParameters.MANGO_CONFIG_FILE);
			addProperty(table, ConfigurationParameters.MAIL_SENDER_HOST);
			addProperty(table, ConfigurationParameters.MAIL_SENDER_PORT);
			addProperty(table, ConfigurationParameters.MAIL_SENDER_USERNAME);
			addProperty(table, ConfigurationParameters.MAIL_SENDER_PASSWORD);
			addProperty(table, ConfigurationParameters.HIBERNATE_SQL_SHOW);
			addProperty(table, ConfigurationParameters.HIBERNATE_SQL_FORMAT);

			String tableString = table.render();
			int tableWidth = tableString.indexOf("\n");

			System.out.println(StringUtils.repeat("=", tableWidth));
			System.out.println("|" + StringUtils.center("Mango Configuration", tableWidth - 2) + "|");
			System.out.println("                     ");
			System.out.println(StringUtils.repeat("=", tableWidth));
			System.out.println("");
			System.out.println(table.render());
			System.out.println("");

			logged = true;
		}
	}

	private void addProperty(Table table, String propertyKey) {
		table.addCell(propertyKey);
		table.addCell(getProperty(propertyKey));
		table.addCell(getProperty(defaultPropertyKey(propertyKey)));
	}

	private String defaultPropertyKey(String key) {
		return key + ".default";
	}

}