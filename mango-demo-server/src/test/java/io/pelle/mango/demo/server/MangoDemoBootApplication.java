package io.pelle.mango.demo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.demo.server.test.MangoDemoWebApplicationContext;
import io.pelle.mango.server.boot.MangoBootApplicationConfigurer;

@Configuration
@SpringBootApplication
@Import({ MangoDemoWebApplicationContext.class })
public class MangoDemoBootApplication extends MangoBootApplicationConfigurer {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(MangoDemoBootApplication.class, args);
	}

}