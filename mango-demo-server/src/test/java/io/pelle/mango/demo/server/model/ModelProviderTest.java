package io.pelle.mango.demo.server.model;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.demo.client.MangoDemoClientConfiguration;
import io.pelle.mango.demo.client.showcase.CompanyVO;

import org.junit.BeforeClass;
import org.junit.Test;

public class ModelProviderTest {

	@BeforeClass
	public static void initModelProvider() {
		MangoDemoClientConfiguration.registerAll();
	}

	@Test
	public void testGetCompanyDictionary() {
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary("Company");
		assertEquals("Company", dictionaryModel.getName());
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidModelId() {
		DictionaryModelProvider.getDictionary("xxx");
	}
	
	@Test
	public void testGetDictionaryModelForCompanyVOClass() {
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionaryModelForClass(CompanyVO.class);
		assertEquals("Company", dictionaryModel.getName());
	}
	
	
}
