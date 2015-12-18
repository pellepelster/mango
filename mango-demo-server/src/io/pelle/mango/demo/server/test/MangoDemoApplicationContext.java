package io.pelle.mango.demo.server.test;

import static io.pelle.mango.server.state.StateBuilder.transition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import io.pelle.mango.MangoSpringInvokerServicesGen;
import io.pelle.mango.MangoSpringServicesGen;
import io.pelle.mango.demo.MangoDemoBaseApplicationContextGen;
import io.pelle.mango.demo.MangoDemoRestRemoteServicesGen;
import io.pelle.mango.demo.MangoDemoSpringInvokerServicesGen;
import io.pelle.mango.demo.MangoDemoSpringServicesGen;
import io.pelle.mango.demo.client.MangoDemoClientConfiguration;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.server.MangoLoggerApplicationContext;
import io.pelle.mango.server.MangoMailApplicationContext;
import io.pelle.mango.server.MangoSecurityConfig;
import io.pelle.mango.server.MangoServerApplicationContext;
import io.pelle.mango.server.search.SearchIndexBuilder;
import io.pelle.mango.server.state.StateBuilder;

@Configuration
@Import({ MangoSpringServicesGen.class, MangoDemoBaseApplicationContextGen.class, MangoServerApplicationContext.class, MangoLoggerApplicationContext.class, MangoSecurityConfig.class, MangoMailApplicationContext.class,
		MangoDemoRestRemoteServicesGen.class, MangoDemoSpringServicesGen.class, MangoDemoSpringInvokerServicesGen.class, MangoSpringInvokerServicesGen.class })
@ImportResource({ "classpath:/MangoDemoWebservices-gen.xml", "classpath:/MangoDemoApplicationContext.xml" })
public class MangoDemoApplicationContext {

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
		result.forDictionary(MangoDemoDictionaryModel.COUNTRY).addAttributes(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_NAME,
				MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_ISO_CODE3, MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_ISO_CODE2);

		return result;
	}

	@Bean
	public StateBuilder createEntity1States() {

		StateBuilder sb = StateBuilder.create(Entity1.class, Entity1VO.STATE1);
		sb.addState("stateA", transition("transitionAtoB", "stateB"));

		return sb;
	}

}
