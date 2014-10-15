package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.MangoGwtAsyncAdapterRemoteServiceLocator;
import io.pelle.mango.client.baseentityservice.IBaseEntityService;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.MangoMessages;
import io.pelle.mango.client.web.test.sync.DictionaryEditorModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.DictionarySearchModuleSyncTestUI;
import io.pelle.mango.client.web.test.sync.MangoClientSyncWebTest;
import io.pelle.mango.client.web.test.sync.controls.BooleanTestControl;
import io.pelle.mango.client.web.test.sync.controls.ControlGroupTest;
import io.pelle.mango.client.web.test.sync.controls.EnumerationTestControl;
import io.pelle.mango.client.web.test.sync.controls.ReferenceTestControl;
import io.pelle.mango.client.web.test.sync.controls.TextTestControl;
import io.pelle.mango.client.web.util.I18NProxy;
import io.pelle.mango.test.client.ENUMERATION1;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.Entity2VO;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoClientTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testDemoDictionary2TextControl2() {

		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity2VO> editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		TextTestControl textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.setValue("abc");
		editor2.save();

		DictionarySearchModuleSyncTestUI<Entity2VO> search2 = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_SEARCH2);
		search2.execute();
		search2.assertSearchResults(1);
		editor2 = search2.openEditor(0);
		textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.assertValue("abc");
	}

	@Test
	public void testDemoDictionary1ReferenceControl1reateSearchAndUpdate() {

		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity2VO> editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		TextTestControl textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.setValue("abc");
		editor2.save();

		editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.setValue("def");
		editor2.save();

		editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.setValue("xxx");
		editor2.save();

		editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.setValue("xyz");
		editor2.save();

		// dictionary1 (ghi)
		DictionaryEditorModuleSyncTestUI<Entity1VO> editor1 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1);
		TextTestControl textControl1 = editor1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TEXT_CONTROL1);
		textControl1.setValue("ghi");

		ReferenceTestControl<Entity2VO> referenceControl1 = editor1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.REFERENCE_CONTROL1);

		referenceControl1.parseValue("x");
		referenceControl1.assertHasSuggestions(2);

		referenceControl1.parseValue("ab");
		referenceControl1.endEdit();
		referenceControl1.assertValueString("abc");
		editor1.save();

		// dictionary1 (jkl)
		editor1 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1);
		textControl1 = editor1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TEXT_CONTROL1);
		textControl1.setValue("jkl");

		referenceControl1 = editor1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.REFERENCE_CONTROL1);
		referenceControl1.parseValue("def");
		referenceControl1.endEdit();
		referenceControl1.assertValueString("def");
		editor1.save();

		// search dictionary1
		DictionarySearchModuleSyncTestUI<Entity1VO> search1 = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1);
		referenceControl1 = search1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.REFERENCE_CONTROL1);
		referenceControl1.parseValue("abc");
		referenceControl1.endEdit();
		search1.execute();
		search1.assertSearchResults(1);
		// assertEquals("abc",
		// search.getResultRow(0).getVO().getStringDatatype1());

	}

	@Test
	public void testDictionary1EditorMetaInformation() {

		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control.setValue("abc");
		assertTrue(editor.getModule().getDictionaryEditor().getMetaInformation().isPresent());
		editor.save();
		assertNull(editor.getModule().getDictionaryEditor().getMetaInformation().get().getCreateUser());

	}

	@Test
	public void testDictionary1TextControl1SaveAndSearch() {

		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control.setValue("abc");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control.setValue("def");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		TextTestControl filterTextControl1 = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
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
	public void testDictionary1TextControl1NaturalKey() {

		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		editor.save();
		control.assertHasErrors();

		control.setValue("abc");
		control.assertHasNoErrors();
		editor.save();

	}

	private DictionaryEditorModuleSyncTestUI<Entity1VO> createTestDictionaryEditor1() {

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.setValue(UUID.randomUUID().toString());

		return editor;
	}

	@Test
	public void testDictionary1BooleanControl1SaveAndSearch() {

		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = createTestDictionaryEditor1();
		BooleanTestControl control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1);
		control.setValue(false);
		editor.save();

		editor = createTestDictionaryEditor1();
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1);
		control.setValue(true);
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		control = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.BOOLEAN_CONTROL1);
		control.setValue(true);
		search.execute();
		search.assertSearchResults(1);

		editor = search.openEditor(0);
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1);
		assertEquals("true", control.getValue().toString());
	}

	@Test
	public void testDictionary1EnumerationControl1SaveAndSearch() {

		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = createTestDictionaryEditor1();
		EnumerationTestControl<ENUMERATION1> control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);

		assertEquals(2, control.getEnumerationMap().size());

		Iterator<Map.Entry<String, String>> iterator = control.getEnumerationMap().entrySet().iterator();

		Map.Entry<String, String> entry1 = iterator.next();
		Map.Entry<String, String> entry2 = iterator.next();

		assertEquals("ENUMERATIONVALUE1", entry1.getKey().toString());
		assertEquals("ENUMERATIONVALUE1", entry1.getValue());
		assertEquals("ENUMERATIONVALUE2", entry2.getKey());
		assertEquals("Value2", entry2.getValue());

		control.parseValue("ENUMERATIONVALUE1");
		editor.save();

		editor = createTestDictionaryEditor1();
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);
		control.parseValue("ENUMERATIONVALUE2");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		control = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.ENUMERATIONCONTROL1);
		control.parseValue("ENUMERATIONVALUE2");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("ENUMERATIONVALUE2", search.getResultRow(0).getVO().getEnumeration1Datatype().toString());

		editor = search.openEditor(0);
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);
		assertEquals("ENUMERATIONVALUE2", control.getValue().toString());
	}

	@Test
	public void testDictionary1ControlGroup1MultiFilter() {

		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());

		DictionaryEditorModuleSyncTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control1 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		TextTestControl control2 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		control1.setValue("abc");
		control2.setValue("cba");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		control1 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control2 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		control1.setValue("abd");
		control2.setValue("dba");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		control1 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control2 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		control1.setValue("abe");
		control2.setValue("abc");
		editor.save();

		DictionarySearchModuleSyncTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(3);

		TextTestControl filterControl1 = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterControl1.setValue("abc");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());

		filterControl1 = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterControl1.setValue("");
		search.execute();
		search.assertSearchResults(3);

		ControlGroupTest filterGroupControlTest = search.getGroupControlTest(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.CONTROL_GROUP1);
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
