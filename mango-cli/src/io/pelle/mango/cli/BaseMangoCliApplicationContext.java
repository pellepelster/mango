package io.pelle.mango.cli;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.MangoSpringInvokerClientServicesGen;

@Configuration
@Import({ MangoSpringInvokerClientServicesGen.class })
public class BaseMangoCliApplicationContext {
	
	@Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertyPlaceholderConfigurer();
	}
	
}
