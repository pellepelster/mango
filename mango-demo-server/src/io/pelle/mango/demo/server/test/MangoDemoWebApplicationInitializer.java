package io.pelle.mango.demo.server.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.server.MangoWebApplicationInitializer;
import io.pelle.mango.server.util.AwsRdsMysqlJndiInjector;

public class MangoDemoWebApplicationInitializer extends MangoWebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		WebApplicationContext context = getContext();

		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.addListener(new AwsRdsMysqlJndiInjector());

		servletContext.setInitParameter("log4jConfigLocation", "/WEB-INF/log4j.properties");
		servletContext.addListener(new Log4jConfigListener());

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("mangoDemo", new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/remote/*");

		ServletRegistration.Dynamic gwtUpload = servletContext.addServlet("gwtUpload", new MyServlet());
		gwtUpload.setLoadOnStartup(1);
		gwtUpload.addMapping("/" + IFileControl.FILE_REQUEST_BASE_URL + "/*");

	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(io.pelle.mango.demo.server.test.MangoDemoWebApplicationContext.class.getName());
		return context;
	}

}