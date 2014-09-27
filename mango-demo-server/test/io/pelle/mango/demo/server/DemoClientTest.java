package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.MangoGwtAsyncAdapterRemoteServiceLocator;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.MangoMessages;
import io.pelle.mango.client.web.test.sync.DictionaryEditorModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.DictionarySearchModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.MangoClientSyncWebTest;
import io.pelle.mango.client.web.test.sync.controls.TextControlTest;
import io.pelle.mango.client.web.util.I18NProxy;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoClientTest extends BaseDemoTest {

	@Test
	public void testEditorSave() {

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);

		TextControlTest textControl1 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue("aaa");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(1);
		assertEquals("aaa", search.getResultRow(0).getVO().getStringDatatype1());

	}

	@Autowired
	public void setMangoGwtAsyncAdapterRemoteServiceLocator(MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator) {
		MangoClientWeb.getInstance().setMyAdminGWTRemoteServiceLocator(mangoGwtAsyncAdapterRemoteServiceLocator);
		MangoClientWeb.MESSAGES = I18NProxy.create(MangoMessages.class);

		MangoDemoClientConfiguration.registerAll();
	}
}
