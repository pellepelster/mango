package io.pelle.mango.demo.server;

import static org.junit.Assert.assertNotNull;
import io.pelle.mango.MangoGwtAsyncAdapterRemoteServiceLocator;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.test.sync.DictionaryEditorModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.MangoClientSyncWebTest;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoDictionaryTest extends BaseDemoTest {

	@Autowired
	private MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator;

	@Test
	public void testCreateAndSaveNaturalKey() {

		MangoClientWeb.getInstance().setMyAdminGWTRemoteServiceLocator(mangoGwtAsyncAdapterRemoteServiceLocator);
		MangoDemoClientConfiguration.registerAll();
		DictionaryEditorModuleSyncTestUI editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		assertNotNull(editor);
	}

	public void setMangoGwtAsyncAdapterRemoteServiceLocator(MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator) {
		this.mangoGwtAsyncAdapterRemoteServiceLocator = mangoGwtAsyncAdapterRemoteServiceLocator;
	}
}
