package io.pelle.mango.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.server.file.FileControlUploadServlet;
import io.pelle.mango.server.util.AwsRdsMysqlJndiInjector;

public abstract class MangoWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	private static final String LOG4J_LOCATION = "/WEB-INF/log4j.properties";

	private static final String REMOTE_SERVLET_NAME = "remote";

	public MangoWebApplicationInitializer(Class<?> ...configLocationClasses) {
		super(configLocationClasses);
	}

	@Override
	public void afterSpringSecurityFilterChain(ServletContext servletContext) {

		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.addListener(new AwsRdsMysqlJndiInjector());
		servletContext.setInitParameter("log4jConfigLocation", LOG4J_LOCATION);
		servletContext.addListener(new Log4jConfigListener());

		ServletRegistration.Dynamic gwtUpload = servletContext.addServlet(IFileControl.FILE_UPLOAD_BASE_URL, new FileControlUploadServlet());
		gwtUpload.setLoadOnStartup(1);
		gwtUpload.addMapping("/" + IFileControl.FILE_UPLOAD_BASE_URL + "/*");

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(REMOTE_SERVLET_NAME, new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/" + REMOTE_SERVLET_NAME + "/*");

	}

}