package io.pelle.mango.demo.server;

import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.server.search.SearchIndexBuilder;

import org.junit.Test;

public class SearchIndexBuilderTest {

	@Test
	public void testCreateSearchIndexForCompanyDictionary() {
		SearchIndexBuilder.createBuilder("index1").forDictionary(MangoDemoDictionaryModel.COMPANY).addAttributes(MangoDemoDictionaryModel.COMPANY.COMPANY_EDITOR.NAME1);
	}

	@Test(expected = RuntimeException.class)
	public void testCreateSearchIndexForCompanyDictionaryInvalidControl() {
		SearchIndexBuilder.createBuilder("index1").forDictionary(MangoDemoDictionaryModel.COMPANY).addAttributes(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_ISO_CODE2);
	}

}
