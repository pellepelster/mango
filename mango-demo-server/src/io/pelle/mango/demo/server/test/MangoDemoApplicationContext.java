package io.pelle.mango.demo.server.test;

import io.pelle.mango.demo.client.MangoDemoClientConfiguration;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.server.MangoServerApplicationContext;
import io.pelle.mango.server.search.SearchIndexBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/MangoDemoApplicationContext.xml", "classpath:/MangoDemoDB-gen.xml", "classpath:/MangoDemoBaseApplicationContext-gen.xml", "classpath:/MangoDemoSpringServices-gen.xml", "classpath:/MangoSpringServices-gen.xml",
		"classpath:/MangoDemoRestRemoteServices-gen.xml", "classpath:/MangoLoggerApplicationContext.xml", "classpath:/MangoDemoSpringInvokerServices-gen.xml", "classpath:/MangoSpringInvokerServices-gen.xml" })
public class MangoDemoApplicationContext extends MangoServerApplicationContext {

	@Bean
	public TestHierarchicalConfiguration testHierarchicalConfiguration() {
		return new TestHierarchicalConfiguration();
	}

	// TODO: this should be in a generated base class
	@Bean
	public MangoDemoClientConfiguration mangoDemoClientConfiguration() {
		return MangoDemoClientConfiguration.registerAll();
	}

	@Bean
	public SearchIndexBuilder createEntity1Index() {
		
		SearchIndexBuilder result = SearchIndexBuilder.createBuilder("index1").setDefault();
		
		result.forDictionary(MangoDemoDictionaryModel.COMPANY).addAttributes(MangoDemoDictionaryModel.COMPANY.COMPANY_EDITOR.NAME1);
		result.forDictionary(MangoDemoDictionaryModel.COUNTRY).addAttributes(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_NAME, MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_ISO_CODE3, MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_ISO_CODE2);
		
		return result;
	}

}
