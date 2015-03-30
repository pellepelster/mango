package io.pelle.mango.server;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.core.property.PropertyBuilder;

public interface ConfigurationParameters {

	public static IProperty<String> MANGO_CONFIG_FILE = PropertyBuilder.createStringSpringProperty("mango.config.file").defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_HOST = PropertyBuilder.createStringSpringProperty("mail.sender.host").defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_PORT = PropertyBuilder.createStringSpringProperty("mail.sender.port").defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_USERNAME = PropertyBuilder.createStringSpringProperty("mail.sender.username").defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_PASSWORD = PropertyBuilder.createStringSpringProperty("mail.sender.password").defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_FROM = PropertyBuilder.createStringSpringProperty("mail.sender.from").defaultValueWithPostfix();

	public static IProperty<Boolean> HIBERNATE_SQL_SHOW = PropertyBuilder.createBooleanSpringProperty("hibernate.sql.show").defaultValueWithPostfix();

	public static IProperty<Boolean> HIBERNATE_SQL_FORMAT = PropertyBuilder.createBooleanSpringProperty("hibernate.sql.format").defaultValueWithPostfix();

}
