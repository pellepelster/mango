package io.pelle.mango.demo.server;

import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
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
		SearchIndexBuilder result = SearchIndexBuilder.createBuilder("index1");
		
		result.forDictionary(MangoDemoDictionaryModel.COMPANY).addAttributes(MangoDemoDictionaryModel.COMPANY.COMPANY_EDITOR.NAME1);
		
		return result;
	}

}
