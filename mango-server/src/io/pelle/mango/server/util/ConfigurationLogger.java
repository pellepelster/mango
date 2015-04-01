package io.pelle.mango.server.util;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.server.ConfigurationParameters;

import org.apache.commons.lang3.StringUtils;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.CellStyle.AbbreviationStyle;
import org.nocrala.tools.texttablefmt.CellStyle.HorizontalAlign;
import org.nocrala.tools.texttablefmt.CellStyle.NullStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.google.common.base.Objects;

public class ConfigurationLogger implements ApplicationListener<ContextRefreshedEvent> {

	private boolean logged = false;

	@Autowired
	private IPropertyService propertyService;

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
			addProperty(table, ConfigurationParameters.MAIL_SENDER_FROM);
			addProperty(table, ConfigurationParameters.HIBERNATE_SQL_SHOW);
			addProperty(table, ConfigurationParameters.HIBERNATE_SQL_FORMAT);
			addProperty(table, ConfigurationParameters.GRAPHITE_METRICS_ENABLED);
			addProperty(table, ConfigurationParameters.GRAPHITE_METRICS_HOST);
			addProperty(table, ConfigurationParameters.GRAPHITE_METRICS_PORT);
			addProperty(table, ConfigurationParameters.GRAPHITE_METRICS_PREFIX);

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

	private void addProperty(Table table, IProperty<?> property) {
		table.addCell(property.getKey());
		table.addCell(Objects.firstNonNull(propertyService.getProperty(property), "<none>").toString());
		table.addCell(Objects.firstNonNull(propertyService.getPropertyDefault(property), "<none>").toString());
	}

}