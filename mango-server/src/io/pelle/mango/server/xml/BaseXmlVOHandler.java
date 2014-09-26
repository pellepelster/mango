package io.pelle.mango.server.xml;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public abstract class BaseXmlVOHandler {

	private final DateTimeFormatter dateFormat = ISODateTimeFormat.date();

	protected String toXML(Object object) {
		if (Date.class.isAssignableFrom(object.getClass())) {
			return this.dateFormat.print(((Date) object).getTime());
		} else {
			return object.toString();
		}
	}

	protected Serializable fromXml(String data, Class<?> targetClass) {
		if (Date.class.isAssignableFrom(targetClass)) {
			return this.dateFormat.parseDateTime(data).toDate();
		} else if (Long.class.isAssignableFrom(targetClass)) {
			return Long.parseLong(data);
		} else if (BigDecimal.class.isAssignableFrom(targetClass)) {
			return new BigDecimal(data);
		} else if (Integer.class.isAssignableFrom(targetClass)) {
			return Integer.parseInt(data);
		} else if (Boolean.class.isAssignableFrom(targetClass)) {
			return Boolean.parseBoolean(data);
		} else {
			return data;
		}
	}
}