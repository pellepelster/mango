package io.pelle.mango.demo.server;

import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.demo.server.test.MangoDemoApplicationContext;
import io.pelle.mango.server.search.SearchIndexBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/MangoDemoApplicationContext.xml", "classpath:/MangoDemoDB-gen.xml", "classpath:/MangoDemoSpringServices-gen.xml", "classpath:/MangoLoggerApplicationContext.xml",
		"classpath:/MangoDemoBaseApplicationContext-gen.xml", "classpath:/MangoSpringServices-gen.xml", "classpath:/MangoDemoWebservices-gen.xml", "classpath:/MangoTestApplicationContext.xml" })
public class MangoDemoTestApplicationContext extends MangoDemoApplicationContext {

	@Bean
	public SearchIndexBuilder createEntity1Index() {
		return SearchIndexBuilder.createIndex("index1").forEntity(Entity1.class).addAttributes(Entity1.STRINGDATATYPE1);
	}

}
