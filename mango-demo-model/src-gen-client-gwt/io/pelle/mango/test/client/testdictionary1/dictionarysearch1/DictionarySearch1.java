
package io.pelle.mango.test.client.testdictionary1.dictionarysearch1;

@java.lang.SuppressWarnings("all")
public class DictionarySearch1 extends io.pelle.mango.client.base.modules.dictionary.model.search.SearchModel {
	
	public io.pelle.mango.test.client.testdictionary1.dictionarysearch1.DictionaryResult1 DICTIONARY_RESULT1 = new io.pelle.mango.test.client.testdictionary1.dictionarysearch1.DictionaryResult1(this);

	public io.pelle.mango.test.client.testdictionary1.dictionarysearch1.DictionaryFilter1 DICTIONARY_FILTER1 = new io.pelle.mango.test.client.testdictionary1.dictionarysearch1.DictionaryFilter1(this);
	
	public DictionarySearch1(io.pelle.mango.client.base.modules.dictionary.model.BaseModel<?> parent) {
		super("DictionarySearch1", parent);
		
		
		// result
		setResultModel(DICTIONARY_RESULT1);

		// filters
		getFilterModels().add(DICTIONARY_FILTER1);
		
	}
	
}
