package io.pelle.mango.demo.server;

import io.pelle.mango.MangoGwtAsyncAdapterRemoteServiceLocator;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.test.sync.DictionaryEditorModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.MangoClientSyncWebTest;
import io.pelle.mango.client.web.test.sync.controls.TextControlTest;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoDictionaryTest extends BaseDemoTest {

	@Autowired
	private MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator;

	@Test
	public void testEditorSave() {

		MangoClientWeb.getInstance().setMyAdminGWTRemoteServiceLocator(mangoGwtAsyncAdapterRemoteServiceLocator);
		MangoDemoClientConfiguration.registerAll();

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		TextControlTest textControl1 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue("aaa");
		editor.save();
	}

	public void setMangoGwtAsyncAdapterRemoteServiceLocator(MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator) {
		this.mangoGwtAsyncAdapterRemoteServiceLocator = mangoGwtAsyncAdapterRemoteServiceLocator;
	}
}
