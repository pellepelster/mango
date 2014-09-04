
package io.pelle.mango.test.client;

public class Dictionary1 extends io.pelle.mango.client.base.modules.navigation.NavigationTreeElement {



	public Dictionary1() {
		super("Dictionary1");

		
		setLabel("Dictionary1");
		
		setModuleLocator("moduleUIName=DictionarySearch&" + io.pelle.mango.client.modules.BaseDictionarySearchModule.SEARCHDICTIONARYNAME_PARAMETER_ID + "=Testdictionary1");
	}

}
