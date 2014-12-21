package io.pelle.mango.server.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class AwsRdsMysqlJndiInjector implements ServletContextListener {

	public static final String RDS_DB_NAME = "RDS_DB_NAME";

	public static final String RDS_USERNAME = "RDS_USERNAME";

	public static final String RDS_PASSWORD = "RDS_PASSWORD";

	public static final String RDS_HOSTNAME = "RDS_HOSTNAME";

	public static final String RDS_PORT = "RDS_PORT";

	public static final String JNDI_NAME = "JNDI_NAME";

	public static final String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";

	public static final String MYSQL_PORT_DEFAULT = "3306";

	@Override
	public void contextDestroyed(ServletContextEvent servletContext) {
	}

	private boolean hasProperty(String name) {
		return getProperty(name) != null;
	}

	private String getProperty(String name) {
		return System.getProperty(name);
	}

	private String getEnvironment(String name, String def) {
		if (hasProperty(name)) {
			return getProperty(name);
		} else {
			return def;
		}
	}

	private void log(ServletContextEvent servletContext, String message) {
		log(servletContext, message, null);
	}

	private void log(ServletContextEvent servletContext, String message, Throwable ex) {
		servletContext.getServletContext().log(getClass().getSimpleName() + ": " + message, ex);
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {

		if (!hasProperty(RDS_HOSTNAME) || !hasProperty(RDS_DB_NAME) || !hasProperty(JNDI_NAME)) {
			log(contextEvent, String.format("environment variables '%s', '%s' or '%s' not found", RDS_HOSTNAME, RDS_DB_NAME, JNDI_NAME));
			return;
		}

		log(contextEvent, String.format("hostname: '%s', db name: '%s', jdni name: '%s'", RDS_HOSTNAME, RDS_DB_NAME, JNDI_NAME));

		try {

			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

			InitialContext ic = new InitialContext();

			ic.createSubcontext("java:");
			ic.createSubcontext("java:/comp");
			ic.createSubcontext("java:/comp/env");
			ic.createSubcontext("java:/comp/env/jdbc");

			BasicDataSource dataSource = new BasicDataSource();

			dataSource.setDriverClassName(MYSQL_DRIVER_NAME);
			dataSource.setUrl(String.format("jdbc:mysql://%s:%s/%s", getProperty(RDS_HOSTNAME), getEnvironment(RDS_PORT, MYSQL_PORT_DEFAULT), getProperty(RDS_DB_NAME)));

			if (hasProperty(RDS_USERNAME)) {
				dataSource.setUsername(getProperty(RDS_USERNAME));
			}

			if (hasProperty(RDS_PASSWORD)) {
				dataSource.setPassword(getProperty(RDS_PASSWORD));
			}

			String fullJndiName = "java:/comp/env/jdbc/" + getProperty(JNDI_NAME);
			ic.bind(fullJndiName, dataSource);

			log(contextEvent, "bound '" + fullJndiName + "' to '" + dataSource.getUrl() + "'");

		} catch (NamingException ex) {
			log(contextEvent, "error creating jndi link", ex);
		}

	}
}
