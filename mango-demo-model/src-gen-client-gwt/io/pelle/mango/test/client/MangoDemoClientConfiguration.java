
	package io.pelle.mango.test.client;
	
		public class MangoDemoClientConfiguration {
	
		private MangoDemoClientConfiguration() {
		}
	
		public static void registerAll()
		{
			registerDictionaries();
			
			registerNavigation();
		}

		public static void registerDictionaries()
		{
			io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider.registerDictionary(io.pelle.mango.test.client.MangoDemoDictionaryModel.TESTDICTIONARY1);
		}

		public static void registerNavigation()
		{
			io.pelle.mango.client.base.modules.navigation.NavigationTreeProvider.addRootNavigationElement(MangoDemoNavigationModel.ROOT);
		}
}
