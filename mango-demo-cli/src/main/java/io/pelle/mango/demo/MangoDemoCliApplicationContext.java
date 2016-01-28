package io.pelle.mango.demo;

import io.pelle.mango.cli.BaseMangoCliApplicationContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MangoDemoCliApplicationContext extends BaseMangoCliApplicationContext {

	@Bean
	public MangoDemoBean mangoDemoBean() {
		return new MangoDemoBean();
	}

}
