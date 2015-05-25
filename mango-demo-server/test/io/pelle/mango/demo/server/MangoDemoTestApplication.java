package io.pelle.mango.demo.server;

import io.pelle.mango.demo.server.test.MangoDemoApplicationContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/MangoDemoApplicationContext.xml", "classpath:/MangoDemoDB-gen.xml", "classpath:/MangoDemoSpringServices-gen.xml", "classpath:/MangoLoggerApplicationContext.xml",
		"classpath:/MangoDemoBaseApplicationContext-gen.xml", "classpath:/MangoSpringServices-gen.xml", "classpath:/MangoDemoWebservices-gen.xml", "classpath:/MangoTestApplicationContext.xml" })
public class MangoDemoTestApplication extends MangoDemoApplicationContext {

}
