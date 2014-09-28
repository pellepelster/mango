package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.MangoGwtAsyncAdapterRemoteServiceLocator;
import io.pelle.mango.client.baseentityservice.IBaseEntityService;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.MangoMessages;
import io.pelle.mango.client.web.test.sync.DictionaryEditorModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.DictionarySearchModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.MangoClientSyncWebTest;
import io.pelle.mango.client.web.test.sync.controls.ControlGroupTest;
import io.pelle.mango.client.web.test.sync.controls.TextControlTest;
import io.pelle.mango.client.web.util.I18NProxy;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoClientTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testEditorSaveAndSearch() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		TextControlTest textControl1 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue("abc");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		textControl1 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue("def");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		TextControlTest filterTextControl1 = search.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterTextControl1.setValue("abc");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());

		// test case insensitive search for text controls
		filterTextControl1.setValue("ABC");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());

		// test partial search for text controls
		filterTextControl1.setValue("ab");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());
	}

	@Test
	public void testControlGroupMultiFilter() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		TextControlTest textControl1 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		TextControlTest textControl2 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		textControl1.setValue("abc");
		textControl2.setValue("cba");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		textControl1 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl2 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		textControl1.setValue("abd");
		textControl2.setValue("dba");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		textControl1 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl2 = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		textControl1.setValue("abe");
		textControl2.setValue("abc");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(3);

		TextControlTest filterTextControl1 = search.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterTextControl1.setValue("abc");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());

		filterTextControl1 = search.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterTextControl1.setValue("");
		search.execute();
		search.assertSearchResults(3);

		ControlGroupTest filterGroupControlTest = search.getGroupControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.CONTROL_GROUP1);
		filterGroupControlTest.parse("abc");

		search.execute();

		assertEquals(1, search.getModule().getDictionarySearch().getDictionaryResult().getHighlightTexts().size());
		assertEquals("abc", search.getModule().getDictionarySearch().getDictionaryResult().getHighlightTexts().iterator().next());

		search.assertSearchResults(2);

		filterGroupControlTest.parse("");
		search.execute();
		search.assertSearchResults(3);

	}

	public void setBaseEntityService(IBaseEntityService baseEntityService) {
		this.baseEntityService = baseEntityService;
	}

	@Autowired
	public void setMangoGwtAsyncAdapterRemoteServiceLocator(MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator) {
		MangoClientWeb.getInstance().setMyAdminGWTRemoteServiceLocator(mangoGwtAsyncAdapterRemoteServiceLocator);
		MangoClientWeb.MESSAGES = I18NProxy.create(MangoMessages.class);
		MangoDemoClientConfiguration.registerAll();
	}
}
