package io.pelle.mango.demo.server.test;


import io.pelle.mango.server.MangoServerApplicationContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/MangoDemoApplicationContext.xml", "classpath:/DBBaseApplicationContext.xml", "classpath:/MangoDemoDB-gen.xml",
		"classpath:/MangoDemoBaseApplicationContext-gen.xml", "classpath:/MangoDemoSpringServices-gen.xml", "classpath:/MangoSpringServices-gen.xml", "classpath:/MangoDemoRestRemoteServices-gen.xml", "classpath:/MangoLoggerApplicationContext.xml" })
public class MangoDemoApplicationContext extends MangoServerApplicationContext {

}
