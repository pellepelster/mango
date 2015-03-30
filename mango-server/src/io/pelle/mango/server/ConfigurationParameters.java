package io.pelle.mango.server;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.PROPERTY_TYPE;
import io.pelle.mango.client.core.property.PropertyBuilder;

public interface ConfigurationParameters {

	public static IProperty<String> MANGO_CONFIG_FILE = PropertyBuilder.createStringProperty("mango.config.file").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_HOST = PropertyBuilder.createStringProperty("mail.sender.host").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_PORT = PropertyBuilder.createStringProperty("mail.sender.port").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_USERNAME = PropertyBuilder.createStringProperty("mail.sender.username").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_PASSWORD = PropertyBuilder.createStringProperty("mail.sender.password").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_FROM = PropertyBuilder.createStringProperty("mail.sender.from").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<Boolean> HIBERNATE_SQL_SHOW = PropertyBuilder.createBooleanProperty("hibernate.sql.show").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<Boolean> HIBERNATE_SQL_FORMAT = PropertyBuilder.createBooleanProperty("hibernate.sql.format").system().fallbackToSpring().defaultValueWithPostfix();

}
