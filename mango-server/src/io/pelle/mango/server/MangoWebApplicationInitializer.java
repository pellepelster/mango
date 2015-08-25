package io.pelle.mango.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.server.file.FileControlUploadServlet;
import io.pelle.mango.server.util.AwsRdsMysqlJndiInjector;

public abstract class MangoWebApplicationInitializer implements WebApplicationInitializer {

	private static final String LOG4J_LOCATION = "/WEB-INF/log4j.properties";

	private static final String REMOTE_SERVLET_NAME = "remote";

	private String configLocation;

	private Class<?> configLocationClass;
	
	public MangoWebApplicationInitializer(String configLocation) {
		super();
		this.configLocation = configLocation;
	}

	public MangoWebApplicationInitializer(Class<?> configLocationClass) {
		super();
		this.configLocationClass = configLocationClass;
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		WebApplicationContext context = getContext();

		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.addListener(new AwsRdsMysqlJndiInjector());
		servletContext.setInitParameter("log4jConfigLocation", LOG4J_LOCATION);
		servletContext.addListener(new Log4jConfigListener());
		

		ServletRegistration.Dynamic gwtUpload = servletContext.addServlet(IFileControl.FILE_REQUEST_BASE_URL, new FileControlUploadServlet());
		gwtUpload.setLoadOnStartup(1);
		gwtUpload.addMapping("/" + IFileControl.FILE_REQUEST_BASE_URL+ "/*");
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(REMOTE_SERVLET_NAME, new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/" + REMOTE_SERVLET_NAME + "/*");

	}

	private AnnotationConfigWebApplicationContext getContext() {
		
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		
		if (configLocation != null) {
			context.setConfigLocation(configLocation);
		}

		if (configLocationClass != null) {
			context.setConfigLocation(configLocationClass.getName());
		}

		return context;
	}


}