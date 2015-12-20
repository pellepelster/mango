package io.pelle.mango.demo.server;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration.EmbeddedTomcat;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import io.pelle.mango.demo.server.test.MangoDemoWebApplicationContext;
import io.pelle.mango.server.boot.HelloController;

@Configuration
@Import({ EmbeddedTomcat.class, WebMvcAutoConfiguration.class, MangoDemoWebApplicationContext.class })
@ActiveProfiles("test")
@EnableAutoConfiguration
public class MangoDemoBootApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(MangoDemoBootApplication.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	@Bean
	public HelloController helloController() {
		return new HelloController();
	}

}