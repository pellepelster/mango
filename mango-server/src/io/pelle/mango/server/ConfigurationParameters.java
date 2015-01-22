package io.pelle.mango.server;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.core.property.PropertyBuilder;

public interface ConfigurationParameters {

	public static IProperty<String> MANGO_CONFIG_FILE = PropertyBuilder.createStringSpringProperty("mango.config.file");

	public static IProperty<String> MAIL_SENDER_HOST = PropertyBuilder.createStringSpringProperty("mail.sender.host");

	public static IProperty<String> MAIL_SENDER_PORT = PropertyBuilder.createStringSpringProperty("mail.sender.port");

	public static IProperty<String> MAIL_SENDER_USERNAME = PropertyBuilder.createStringSpringProperty("mail.sender.username");

	public static IProperty<String> MAIL_SENDER_PASSWORD = PropertyBuilder.createStringSpringProperty("mail.sender.password");

	public static IProperty<String> MAIL_SENDER_FROM = PropertyBuilder.createStringSpringProperty("mail.sender.from");

	public static IProperty<String> HIBERNATE_SQL_SHOW = PropertyBuilder.createStringSpringProperty("hibernate.sql.show");

	public static IProperty<String> HIBERNATE_SQL_FORMAT = PropertyBuilder.createStringSpringProperty("hibernate.sql.format");

}
