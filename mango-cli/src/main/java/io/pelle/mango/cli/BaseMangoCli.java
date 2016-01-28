package io.pelle.mango.cli;

import io.pelle.mango.client.system.ISystemService;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.beust.jcommander.JCommander;

public class BaseMangoCli {

	private static BaseMangoCli instance = null;
	
	private static Logger LOG = Logger.getLogger(BaseMangoCli.class);

	private ApplicationContext applicationContext;
	
	private BaseMangoCli(Class<? extends BaseMangoCliApplicationContext> annotatedClass, String[] args) {
		
		CliOptions clientOptions = new CliOptions();
		new JCommander(clientOptions, args);

		LOG.info(String.format("mango.base.remote.url: %s", clientOptions.getUrl()));
		System.setProperty("mango.base.remote.url", clientOptions.getUrl());
		
		applicationContext = new AnnotationConfigApplicationContext(annotatedClass);
		ISystemService systemService = applicationContext.getBean(ISystemService.class);
		LOG.info(String.format("remote host name: %s", systemService.getSystemInformation().getHostName()));
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static BaseMangoCli create(Class<? extends BaseMangoCliApplicationContext> annotatedClass, String[] args) {
		
		if (instance == null) {
			instance = new BaseMangoCli(annotatedClass, args);
		}
		
		return instance; 
	}

}
