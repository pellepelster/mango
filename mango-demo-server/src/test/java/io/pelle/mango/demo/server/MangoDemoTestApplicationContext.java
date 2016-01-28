package io.pelle.mango.demo.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.demo.server.test.MangoDemoApplicationContext;

@Configuration
@Import(MangoDemoApplicationContext.class)
public class MangoDemoTestApplicationContext {

	@Bean
	public SpringMangoImplProvider springMangoImplProvider() {
		return new SpringMangoImplProvider();
	}

}
