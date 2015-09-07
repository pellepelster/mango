package io.pelle.mango.server;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.core.property.PropertyBuilder;

public interface ConfigurationParameters {

	public static IProperty<String> MANGO_CONFIG_FILE = PropertyBuilder.getInstance().createStringProperty("mango.config.file").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_HOST = PropertyBuilder.getInstance().createStringProperty("mail.sender.host").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_PORT = PropertyBuilder.getInstance().createStringProperty("mail.sender.port").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_USERNAME = PropertyBuilder.getInstance().createStringProperty("mail.sender.username").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_PASSWORD = PropertyBuilder.getInstance().createStringProperty("mail.sender.password").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<String> MAIL_SENDER_FROM = PropertyBuilder.getInstance().createStringProperty("mail.sender.from").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<Boolean> HIBERNATE_SQL_SHOW = PropertyBuilder.getInstance().createBooleanProperty("hibernate.sql.show").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<Boolean> HIBERNATE_SQL_FORMAT = PropertyBuilder.getInstance().createBooleanProperty("hibernate.sql.format").system().fallbackToSpring().defaultValueWithPostfix();

	public static IProperty<Boolean> GRAPHITE_METRICS_ENABLED = PropertyBuilder.getInstance().createBooleanProperty("graphite.metrics.enabled").system().fallbackToSpring().defaultValue(false);

	public static IProperty<String> GRAPHITE_CARBON_HOST = PropertyBuilder.getInstance().createStringProperty("graphite.carbon.host").system().fallbackToSpring().defaultValue("localhost");

	public static IProperty<Integer> GRAPHITE_CARBON_PORT = PropertyBuilder.getInstance().createIntegerProperty("graphite.carbon.port").system().fallbackToSpring().defaultValue(2003);

	public static IProperty<String> GRAPHITE_EVENTSAPI_URL = PropertyBuilder.getInstance().createStringProperty("graphite.eventsapi.url").system().fallbackToSpring();

	public static IProperty<String> GRAPHITE_METRICS_PREFIX = PropertyBuilder.getInstance().createStringProperty("graphite.metrics.prefix").system().fallbackToSpring();

	public static IProperty<Boolean> SECURITY_DISABLE = PropertyBuilder.getInstance().createBooleanProperty("security.disable").defaultValue(false).system().fallbackToSpring();

}
