package io.pelle.mango.demo.server;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.event.dom.client.ClickEvent;

import io.pelle.mango.MangoGwtAsyncAdapterRemoteServiceLocator;
import io.pelle.mango.client.base.modules.dictionary.DictionaryContext;
import io.pelle.mango.client.base.modules.dictionary.controls.BaseButton;
import io.pelle.mango.client.base.modules.dictionary.hooks.BaseSearchHook;
import io.pelle.mango.client.base.modules.dictionary.hooks.BaseTableHook;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.MangoMessages;
import io.pelle.mango.client.web.test.DictionaryEditorModuleTestUI;
import io.pelle.mango.client.web.test.DictionarySearchModuleTestUI;
import io.pelle.mango.client.web.test.MangoClientSyncWebTest;
import io.pelle.mango.client.web.test.TestButton;
import io.pelle.mango.client.web.test.container.TabFolderTestContainer;
import io.pelle.mango.client.web.test.controls.BooleanTestControl;
import io.pelle.mango.client.web.test.controls.ControlGroupTestControl;
import io.pelle.mango.client.web.test.controls.DateTestControl;
import io.pelle.mango.client.web.test.controls.DecimalTestControl;
import io.pelle.mango.client.web.test.controls.EnumerationTestControl;
import io.pelle.mango.client.web.test.controls.IntegerTestControl;
import io.pelle.mango.client.web.test.controls.ReferenceTestControl;
import io.pelle.mango.client.web.test.controls.TextTestControl;
import io.pelle.mango.client.web.util.I18NProxy;
import io.pelle.mango.demo.client.MangoDemoClientConfiguration;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.client.showcase.CurrencyVO;
import io.pelle.mango.demo.client.test.ENUMERATION1;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity3VO;

public class DemoClientTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void beforeEach() {
		baseEntityService.deleteAll(Entity1VO.class.getName());
		baseEntityService.deleteAll(Entity2VO.class.getName());
		baseEntityService.deleteAll(Entity3VO.class.getName());
		baseEntityService.deleteAll(CountryVO.class.getName());

		DictionaryHookRegistry.getInstance().clearAll();
	}

	@Test
	public void testBaseTableHookRowColor() {

		MangoDemoDictionaryModel.COUNTRY.COUNTRY_SEARCH.COUNTRY_RESULT.setTableHook(new BaseTableHook<CountryVO>() {
			@Override
			public String getStyleName(CountryVO tableRow) {
				if (tableRow.getCountryIsoCode2().equals("DE")) {
					return "style1";
				} else {
					return super.getStyleName(tableRow);
				}
			}
		});

		DictionaryEditorModuleTestUI<CountryVO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR);
		TextTestControl control = editor.getControl(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_ISO_CODE2);
		control.enterValue("DE");
		editor.save();

		DictionarySearchModuleTestUI<Entity2VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.COUNTRY.COUNTRY_SEARCH);
		search.execute();
		search.assertSearchResults(1);

		assertEquals("style1", search.getResultRow(0).getStyleNames());
	}

	@Test
	public void testCountryEditorExchangeRateReadOnly() {

		DictionaryEditorModuleTestUI<CountryVO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR);
		DecimalTestControl readOnlyControl = editor.getControl(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_EXCHANGE_RATE);
		readOnlyControl.assertReadOnly();
		readOnlyControl.enterValue("abc");
		readOnlyControl.assertValueString("");
	}

	@Test
	public void testDemoDictionary2TextControl2() {

		final AtomicBoolean called = new AtomicBoolean(false);

		MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_SEARCH2.addSearchHook(new BaseSearchHook<Entity2VO>() {

			@Override
			public SelectQuery<Entity2VO> beforeSearch(SelectQuery<Entity2VO> selectQuery) {
				called.set(true);
				return super.beforeSearch(selectQuery);
			}
		});

		DictionaryEditorModuleTestUI<Entity2VO> editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		TextTestControl textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.enterValue("abc");
		editor2.save();

		DictionarySearchModuleTestUI<Entity2VO> search2 = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_SEARCH2);
		search2.execute();
		search2.assertSearchResults(1);
		editor2 = search2.openEditor(0);
		textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.assertValue("abc");

		await().untilAtomic(called, equalTo(true));

	}

	@Test
	public void testDemoDictionary1ReferenceControl1() {

		DictionaryEditorModuleTestUI<Entity2VO> editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		TextTestControl textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.enterValue("abc");
		editor2.save();

		editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.enterValue("def");
		editor2.save();

		editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.enterValue("xxx");
		editor2.save();

		editor2 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2);
		textControl2 = editor2.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY2.DEMO_EDITOR2.TEXT_CONTROL2);
		textControl2.enterValue("xyz");
		editor2.save();

		// dictionary1 (ghi)
		DictionaryEditorModuleTestUI<Entity1VO> editor1 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1);
		TextTestControl textControl1 = editor1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.TEXT_CONTROL1);
		textControl1.enterValue("ghi");

		ReferenceTestControl<Entity2VO> referenceControl1 = editor1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.REFERENCE_CONTROL1);

		referenceControl1.enterValue("x");
		referenceControl1.assertHasSuggestions(2);

		referenceControl1.enterValue("1234");
		referenceControl1.leaveControl();
		referenceControl1.assertHasErrorWithText("'1234' could not be found");
		referenceControl1.enterValue("ab");
		editor1.save();
		referenceControl1.assertValueString("abc");

		// dictionary1 (jkl)
		editor1 = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1);
		textControl1 = editor1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.TEXT_CONTROL1);
		textControl1.enterValue("jkl");

		referenceControl1 = editor1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.REFERENCE_CONTROL1);
		referenceControl1.enterValue("def");
		editor1.save();
		referenceControl1.assertValueString("def");

		// search dictionary1
		DictionarySearchModuleTestUI<Entity1VO> search1 = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1);
		referenceControl1 = search1.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.REFERENCE_CONTROL1);
		referenceControl1.enterValue("abc");
		search1.execute();
		search1.assertSearchResults(1);

		// remove reference
		referenceControl1.enterValue("");
		search1.execute();
		search1.assertSearchResults(2);

		// assertEquals("abc",
		// search.getResultRow(0).getVO().getStringDatatype1());

	}

	@Test
	public void testDictionary1EditorMetaInformation() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control.enterValue("abc");
		assertTrue(editor.getModule().getDictionaryEditor().getMetaInformation().isPresent());
		editor.save();
		assertNull(editor.getModule().getDictionaryEditor().getMetaInformation().get().getCreateUser());

	}

	@Test
	public void testTabFolder() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1);
		TabFolderTestContainer container = editor.getContainer(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1);
		container.assertTabCount(2);

	}

	@Test
	public void testDictionary1TextControl1() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control.enterValue("abc");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control.enterValue("def");
		editor.save();

		DictionarySearchModuleTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		TextTestControl filterTextControl1 = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterTextControl1.enterValue("abc");

		// filter should not show errors
		filterTextControl1.enterValue("");
		filterTextControl1.assertHasNoErrors();

		filterTextControl1.enterValue("abc");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());

		// test case insensitive search for text controls
		filterTextControl1.enterValue("ABC");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());

		// test partial search for text controls
		filterTextControl1.enterValue("ab");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());
	}

	@Test
	@Ignore
	public void testDictionary1TextControl4DefaultWidth() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB2.TEXT_CONTROL4);
	}

	@Test
	public void testDictionary1TextControl1NaturalKey() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		editor.save();
		control.assertHasErrors();

		control.enterValue("abc");
		control.assertHasNoErrors();
		editor.save();

	}

	private DictionaryEditorModuleTestUI<Entity1VO> createTestDictionaryEditor1() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl1.enterValue(UUID.randomUUID().toString());

		return editor;
	}

	private DictionaryEditorModuleTestUI<Entity1VO> createDemoDictionary1Editor1() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1);
		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.TEXT_CONTROL1);
		textControl1.enterValue(UUID.randomUUID().toString());

		return editor;
	}

	@Test
	public void testDictionary1AddEditorButton() {

		final AtomicBoolean called = new AtomicBoolean(false);

		MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.addEditorButton(new BaseButton("id1") {

			@Override
			public void onClick(ClickEvent event, DictionaryContext dictionaryContext) {
				called.set(true);

				assertTrue(dictionaryContext.hasEditor());
				assertEquals("DemoEditor1", dictionaryContext.getDictionaryEditor().getModel().getName());
			}
		});

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1);

		TestButton button = Iterables.find(editor.getButtons(), new Predicate<TestButton>() {
			@Override
			public boolean apply(TestButton button) {
				return button.getId().equals("id1");
			}
		});

		button.push();
		// assertTrue();

		await().untilAtomic(called, equalTo(true));
	}

	@Test
	public void testDictionary1DecimalControl1() {

		// create 1
		DictionaryEditorModuleTestUI<Entity1VO> editor = createDemoDictionary1Editor1();
		DecimalTestControl control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.DECIMAL_CONTROL1);
		control.enterValue("a");
		control.assertHasErrorWithText("'a' is not a valid decimal");
		control.enterValue("1.2");
		editor.save();

		// create 2
		editor = createDemoDictionary1Editor1();
		control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.DECIMAL_CONTROL1);
		control.enterValue("2.6");
		editor.save();

		// search all
		DictionarySearchModuleTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		// search 1
		control = search.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.DECIMAL_CONTROL1);
		control.enterValue("1.2");
		search.execute();
		search.assertSearchResults(1);

		// search 2
		control = search.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.DECIMAL_CONTROL1);
		control.enterValue("2.6");
		search.execute();
		search.assertSearchResults(1);

		editor = search.openEditor(0);
		control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.DECIMAL_CONTROL1);
		assertEquals("2.6", control.getValueString());
	}

	@Test
	public void testDictionary1IntegerControl1() {

		// create 1
		DictionaryEditorModuleTestUI<Entity1VO> editor = createDemoDictionary1Editor1();
		IntegerTestControl control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.INTEGER_CONTROL1);
		control.enterValue("a");
		control.assertHasErrorWithText("'a' is not a valid integer");
		control.enterValue("1");
		editor.save();

		// create 2
		editor = createDemoDictionary1Editor1();
		control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.INTEGER_CONTROL1);
		control.enterValue("2");
		editor.save();

		// search all
		DictionarySearchModuleTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		// search 1
		control = search.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.INTEGER_CONTROL1);
		control.enterValue("1");
		search.execute();
		search.assertSearchResults(1);

		// search 2
		control = search.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.INTEGER_CONTROL1);
		control.enterValue("2");
		search.execute();
		search.assertSearchResults(1);

		editor = search.openEditor(0);
		control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.INTEGER_CONTROL1);
		assertEquals("2", control.getValueString());
	}

	@Test
	public void testDictionary1DirtyHandling() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1);

		editor.assertNotDirty();

		TextTestControl textControl1 = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.TEXT_CONTROL1);
		textControl1.enterValue(UUID.randomUUID().toString());
		editor.assertDirty();

		editor.save();
		editor.assertNotDirty();
	}

	@Test
	public void testDictionary1DateControl1() {

		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		String date1 = df.format(new Date(2014, 8, 28));
		String date2 = df.format(new Date(2015, 9, 29));

		// create 1
		DictionaryEditorModuleTestUI<Entity1VO> editor = createDemoDictionary1Editor1();
		DateTestControl control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.DATE_CONTROL1);
		control.enterValue("a");
		control.assertHasErrorWithText("'a' is not a valid date");
		control.enterValue(date1);
		editor.save();

		// create 2
		editor = createDemoDictionary1Editor1();
		control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.DATE_CONTROL1);
		control.enterValue(date2);
		editor.save();

		// search all
		DictionarySearchModuleTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		// search 1
		control = search.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.DATE_CONTROL1);
		control.enterValue(date1);
		search.execute();
		search.assertSearchResults(1);

		// search 2
		control = search.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.DATE_CONTROL1);
		control.enterValue(date2);
		search.execute();
		search.assertSearchResults(1);

		editor = search.openEditor(0);
		control = editor.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TABFOLDER1.TAB1.DATE_CONTROL1);
		assertEquals(date2, control.getValueString());
	}

	@Test
	public void testDictionary1BooleanControl1() {

		// create true value
		DictionaryEditorModuleTestUI<Entity1VO> editor = createTestDictionaryEditor1();
		BooleanTestControl control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1);
		control.uncheck();
		editor.save();

		// create false value
		editor = createTestDictionaryEditor1();
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1);
		control.check();
		editor.save();

		// search all
		DictionarySearchModuleTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		// search false
		control = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.BOOLEAN_CONTROL1);
		control.uncheck();
		search.execute();
		search.assertSearchResults(1);

		// search true
		control = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.BOOLEAN_CONTROL1);
		control.check();
		search.execute();
		search.assertSearchResults(1);

		editor = search.openEditor(0);
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1);
		assertEquals("true", control.getValueString());
	}

	@Test
	public void testDictionary1EnumerationControlWithouEntityAttrbiute() {

		final AtomicBoolean called = new AtomicBoolean(false);

		MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.addSearchHook(new BaseSearchHook<Entity1VO>() {

			@Override
			public SelectQuery<Entity1VO> beforeSearch(SelectQuery<Entity1VO> selectQuery) {

				assertEquals(ENUMERATION1.ENUMERATIONVALUE1, selectQuery.getData().get(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.ENUMERATION_CONTROL1_WITHOUT_ATTRIBUTE.getFullQualifiedName()));

				called.set(true);

				return super.beforeSearch(selectQuery);
			}
		});

		DictionarySearchModuleTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1);

		EnumerationTestControl<ENUMERATION1> control = search.getControl(MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_SEARCH1.DEMO_FILTER1.ENUMERATION_CONTROL1_WITHOUT_ATTRIBUTE);
		control.setValue(ENUMERATION1.ENUMERATIONVALUE1);
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, control.getValue());

		search.execute();

		await().untilAtomic(called, equalTo(true));

	}

	@Test
	public void testDictionary1EnumerationControl1() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = createTestDictionaryEditor1();
		EnumerationTestControl<ENUMERATION1> control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);

		assertEquals(2, control.getEnumerationMap().size());

		Iterator<Map.Entry<String, String>> iterator = control.getEnumerationMap().entrySet().iterator();

		Map.Entry<String, String> entry1 = iterator.next();
		Map.Entry<String, String> entry2 = iterator.next();

		assertEquals("ENUMERATIONVALUE1", entry1.getKey().toString());
		assertEquals("ENUMERATIONVALUE1", entry1.getValue());
		assertEquals("ENUMERATIONVALUE2", entry2.getKey());
		assertEquals("Value2", entry2.getValue());

		control.enterValue("ENUMERATIONVALUE1");
		editor.save();

		editor = createTestDictionaryEditor1();
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);
		control.enterValue("ENUMERATIONVALUE2");
		editor.save();

		DictionarySearchModuleTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(2);

		control = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.ENUMERATIONCONTROL1);
		control.enterValue("ENUMERATIONVALUE2");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("ENUMERATIONVALUE2", search.getResultRow(0).getVO().getEnumeration1Datatype().toString());

		editor = search.openEditor(0);
		control = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.ENUMERATIONCONTROL1);
		assertEquals("Value2", control.getValueString());
	}

	@Test
	public void testDictionary1ControlGroup1MultiFilter() {

		DictionaryEditorModuleTestUI<Entity1VO> editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		TextTestControl control1 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		TextTestControl control2 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		control1.enterValue("abc");
		control2.enterValue("cba");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		control1 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control2 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		control1.enterValue("abd");
		control2.enterValue("dba");
		editor.save();

		editor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1);
		control1 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		control2 = editor.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL2);
		control1.enterValue("abe");
		control2.enterValue("abc");
		editor.save();

		DictionarySearchModuleTestUI<Entity1VO> search = MangoClientSyncWebTest.getInstance().openSearch(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1);
		search.execute();
		search.assertSearchResults(3);

		TextTestControl filterControl1 = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterControl1.enterValue("abc");
		search.execute();
		search.assertSearchResults(1);
		assertEquals("abc", search.getResultRow(0).getVO().getStringDatatype1());

		filterControl1 = search.getControl(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		filterControl1.enterValue("");
		search.execute();
		search.assertSearchResults(3);

		ControlGroupTestControl filterGroupControlTest = search.getGroupControlTest(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.CONTROL_GROUP1);
		filterGroupControlTest.enterValue("abc");

		search.execute();

		assertEquals(1, search.getModule().getDictionarySearch().getDictionaryResult().getHighlightTexts().size());
		assertEquals("abc", search.getModule().getDictionarySearch().getDictionaryResult().getHighlightTexts().iterator().next());

		search.assertSearchResults(2);

		filterGroupControlTest.enterValue("");
		search.execute();
		search.assertSearchResults(3);

	}

	@Test
	public void testCountryReferenceControl() {

		baseEntityService.deleteAll(CountryVO.class.getName());
		baseEntityService.deleteAll(CurrencyVO.class.getName());

		DictionaryEditorModuleTestUI<CurrencyVO> currencyEditor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.CURRENCY.CURRENCY_EDITOR);
		currencyEditor.getControl(MangoDemoDictionaryModel.CURRENCY.CURRENCY_EDITOR.CURRENCY_NAME).setValue("AA");
		currencyEditor.getControl(MangoDemoDictionaryModel.CURRENCY.CURRENCY_EDITOR.CURRENCY_ISO_CODE).setValue("AAA");
		currencyEditor.save();
		currencyEditor.assertHasNoErrors();

		currencyEditor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.CURRENCY.CURRENCY_EDITOR);
		currencyEditor.getControl(MangoDemoDictionaryModel.CURRENCY.CURRENCY_EDITOR.CURRENCY_NAME).setValue("AB");
		currencyEditor.getControl(MangoDemoDictionaryModel.CURRENCY.CURRENCY_EDITOR.CURRENCY_ISO_CODE).setValue("ABB");
		currencyEditor.save();
		currencyEditor.assertHasNoErrors();

		DictionaryEditorModuleTestUI<CountryVO> countryEditor = MangoClientSyncWebTest.getInstance().openEditor(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR);
		countryEditor.getControl(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_ISO_CODE2).setValue("BB");
		countryEditor.getControl(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_ISO_CODE3).setValue("BBB");
		countryEditor.getControl(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_NAME).setValue("BBBB");

		ReferenceTestControl<CurrencyVO> currencyControl = countryEditor.getControl(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_CURRENCY);
		currencyControl.enterValue("a");
		currencyControl.assertHasSuggestions(2);

		currencyControl.enterValue("aa");
		currencyControl.assertHasSuggestions(1);

	}

	@Autowired
	public void setMangoGwtAsyncAdapterRemoteServiceLocator(MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator) {
		MangoClientWeb.getInstance().setMyAdminGWTRemoteServiceLocator(mangoGwtAsyncAdapterRemoteServiceLocator);
		MangoClientWeb.MESSAGES = I18NProxy.create(MangoMessages.class);
		MangoDemoClientConfiguration.registerAll();
	}
}
