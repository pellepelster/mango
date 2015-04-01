package io.pelle.mango.server;

import io.pelle.mango.client.base.property.IProperty;
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

	public static IProperty<Boolean> GRAPHITE_METRICS_ENABLED = PropertyBuilder.createBooleanProperty("graphite.metrics.enabled").system().fallbackToSpring().defaultValue(false);

	public static IProperty<String> GRAPHITE_METRICS_HOST = PropertyBuilder.createStringProperty("graphite.metrics.host").system().fallbackToSpring().defaultValue("localhost");

	public static IProperty<Integer> GRAPHITE_METRICS_PORT = PropertyBuilder.createIntegerProperty("graphite.metrics.port").system().fallbackToSpring().defaultValue(2003);

	public static IProperty<String> GRAPHITE_METRICS_PREFIX = PropertyBuilder.createStringProperty("graphite.metrics.prefix").system().fallbackToSpring();

}
