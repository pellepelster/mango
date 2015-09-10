package io.pelle.mango.demo.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.pelle.mango.demo.server.test.MangoDemoApplicationContext;

@Configuration
public class MangoDemoTestApplicationContext extends MangoDemoApplicationContext {
	
	@Bean 
	public SpringMangoImplProvider springMangoImplProvider() {
		return new SpringMangoImplProvider();
	}
	
}
