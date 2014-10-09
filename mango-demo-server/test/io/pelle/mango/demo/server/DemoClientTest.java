package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.MangoGwtAsyncAdapterRemoteServiceLocator;
import io.pelle.mango.client.base.modules.dictionary.hooks.BaseEditorHook;
import io.pelle.mango.client.baseentityservice.IBaseEntityService;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.MangoMessages;
import io.pelle.mango.client.web.test.sync.DictionaryEditorModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.DictionarySearchModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.MangoClientSyncWebTest;
import io.pelle.mango.client.web.test.sync.controls.ControlGroupTest;
import io.pelle.mango.client.web.test.sync.controls.EnumerationTestControl;
import io.pelle.mango.client.web.test.sync.controls.TextTestControl;
import io.pelle.mango.client.web.util.I18NProxy;
import io.pelle.mango.test.client.ENUMERATION1;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DemoClientTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testEditorMetaInformation() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue("abc");
		assertTrue(editor.getModule().getDictionaryEditor().getMetaInformation().isPresent());
		editor.save();
		assertNull(editor.getModule().getDictionaryEditor().getMetaInformation().get().getCreateUser());

	}

	public void blog() {

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DICTIONARY1.EDITOR1);
		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.DICTIONARY1.EDITOR1.TEXT1);
		textControl1.setValue("abc");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DICTIONARY1.SEARCH1);
		search.execute();
		search.assertSearchResults(1);

		MangoDemoDictionaryModel.DICTIONARY1.EDITOR1.addEditorHook(new BaseEditorHook<Entity1VO>() {

			@Override
			public void onSave(AsyncCallback<Boolean> asyncCallback, Entity1VO vo) {

				if (vo.getStringDatatype1().equals("xxx")) {
					asyncCallback.onSuccess(false);
				} else {
					asyncCallback.onSuccess(false);
				}
			}
		});

	}

	@Test
	public void testEditorTextControlSaveAndSearch() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue("abc");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		textControl1 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue("def");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		TextTestControl filterTextControl1 = search.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
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

	private DictionaryEditorModuleSyncTestUI<Entity1VO> createTestDictionaryEditor1() {

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue(UUID.randomUUID().toString());

		return editor;
	}

	@Test
	public void testEditorEnumerationControlSaveAndSearch() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = createTestDictionaryEditor1();
		EnumerationTestControl<ENUMERATION1> enumerationControl = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);

		assertEquals(2, enumerationControl.getEnumerationMap().size());

		Iterator<Map.Entry<String, String>> iterator = enumerationControl.getEnumerationMap().entrySet().iterator();

		Map.Entry<String, String> entry1 = iterator.next();
		Map.Entry<String, String> entry2 = iterator.next();

		assertEquals("ENUMERATIONVALUE1", entry1.getKey().toString());
		assertEquals("ENUMERATIONVALUE1", entry1.getValue());
		assertEquals("ENUMERATIONVALUE2", entry2.getKey());
		assertEquals("Value2", entry2.getValue());

		enumerationControl.parseValue("ENUMERATIONVALUE1");
		editor.save();

		editor = createTestDictionaryEditor1();
		enumerationControl = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);
		enumerationControl.parseValue("ENUMERATIONVALUE2");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		enumerationControl = search.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.ENUMERATIONCONTROL1);
		enumerationControl.parseValue("ENUMERATIONVALUE2");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("ENUMERATIONVALUE2", search.getResultRow(0).getVO().getEnumeration1Datatype().toString());

		editor = search.openEditor(0);
		enumerationControl = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);
		assertEquals("ENUMERATIONVALUE2", enumerationControl.getValue().toString());
	}

	@Test
	public void testControlGroupMultiFilter() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		TextTestControl textControl2 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		textControl1.setValue("abc");
		textControl2.setValue("cba");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		textControl1 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl2 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		textControl1.setValue("abd");
		textControl2.setValue("dba");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1);
		textControl1 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl2 = editor.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		textControl1.setValue("abe");
		textControl2.setValue("abc");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(3);

		TextTestControl filterTextControl1 = search.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterTextControl1.setValue("abc");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());

		filterTextControl1 = search.getControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterTextControl1.setValue("");
		search.execute();
		search.assertSearchResults(3);

		ControlGroupTest filterGroupControlTest = search.getGroupControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.CONTROL_GROUP1);
		filterGroupControlTest.parseValue("abc");

		search.execute();

		assertEquals(1, search.getModule().getDictionarySearch().getDictionaryResult().getHighlightTexts().size());
		assertEquals("abc", search.getModule().getDictionarySearch().getDictionaryResult().getHighlightTexts().iterator().next());

		search.assertSearchResults(2);

		filterGroupControlTest.parseValue("");
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
