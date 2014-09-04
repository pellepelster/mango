
package io.pelle.mango.test.client.testdictionary1;

@java.lang.SuppressWarnings("all")
public class Testdictionary1 extends io.pelle.mango.client.base.modules.dictionary.model.DictionaryModel {

	// label controls

	// dictionary editor
	public io.pelle.mango.test.client.testdictionary1.dictionaryeditor1.DictionaryEditor1 DICTIONARY_EDITOR1 = new io.pelle.mango.test.client.testdictionary1.dictionaryeditor1.DictionaryEditor1(this);

	// dictionary search
	public io.pelle.mango.test.client.testdictionary1.dictionarysearch1.DictionarySearch1 DICTIONARY_SEARCH1 = new io.pelle.mango.test.client.testdictionary1.dictionarysearch1.DictionarySearch1(this);

	public Testdictionary1() {
	
		super("Testdictionary1", null);

		setVoName(io.pelle.mango.test.client.Entity1VO.class);
		
		// label controls
		
		// dictionary editor 'DictionaryEditor1'
		setEditorModel(DICTIONARY_EDITOR1);

		// dictionary search 'DictionarySearch1'
		setSearchModel(DICTIONARY_SEARCH1);
		

		
	}
}
