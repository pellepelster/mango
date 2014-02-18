/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.demo.client.test;

import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper;
import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.GwtTestCaseAdapter;
import io.pelle.mango.client.web.test.MangoClientWebTest;
import io.pelle.mango.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.DictionarySearchModuleTestUIAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.TextControlTestAsyncHelper;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import java.util.List;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class DemoClientDictionary1Test extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "io.pelle.mango.demo.DemoClientTest";
	}

	@Test
	public void testSimpleCreateAndSearchOpenEditor(MangoAsyncGwtTestHelper<Entity1VO> mangoTestHelper, long id, String text) {

		DictionaryEditorModuleTestUIAsyncHelper<Entity1VO> editor = mangoTestHelper.openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1, id);

		TextControlTestAsyncHelper textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl.assertValue(text);
		
		mangoTestHelper.runAsyncTests();
	}

	@Test
	public void testSimpleCreateAndSearch() {

		MangoClientWebTest.getInstance();
		MangoDemoClientConfiguration.registerAll();
		final MangoAsyncGwtTestHelper<Entity1VO> mangoTestHelper = new MangoAsyncGwtTestHelper<Entity1VO>(new GwtTestCaseAdapter() {

			@Override
			public void finishTest() {
				DemoClientDictionary1Test.this.finishTest();
			}

			@Override
			public void delayTestFinish(int delay) {
				DemoClientDictionary1Test.this.delayTestFinish(delay);
			}
		});

		DictionaryEditorModuleTestUIAsyncHelper<Entity1VO> editor = mangoTestHelper.openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		final String text = UUID.uuid();

		// text control
		TextControlTestAsyncHelper textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl.setValue(text);
		editor.save();

		DictionarySearchModuleTestUIAsyncHelper<Entity1VO> search = mangoTestHelper.openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1);
		textControl = search.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		textControl.setValue(text);
		search.execute();
		search.assertResultCount(1);

		search.getResultList(new BaseErrorAsyncCallback<List<ITableRow<Entity1VO>>>() {
			@Override
			public void onSuccess(List<ITableRow<Entity1VO>> result) {
				testSimpleCreateAndSearchOpenEditor(mangoTestHelper, result.get(0).getVO().getId(), text);
			}
		});

		mangoTestHelper.runAsyncTests();
	}

	/**
	 * @Test
	 * @Ignore public void testTextControl() {
	 * 
	 *         MangoClientWebTest.getInstance();
	 *         MangoDemoClientConfiguration.registerAll();
	 * 
	 *         DictionaryEditorModuleTestUIAsyncHelper<Entity1VO> editor =
	 *         openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);
	 * 
	 *         // text control TextControlTestAsyncHelper textControl =
	 *         editor.getTextControlTest
	 *         (MangoDemoDictionaryModel.TESTDICTIONARY1
	 *         .DICTIONARY_EDITOR1.TEXTCONTROL1); //
	 *         textControl.assertMandatory(); // //
	 *         textControl.assertHasNoErrors(); // textControl.setValue("xxx");
	 *         // // textControl.assertHasNoErrors(); //
	 *         textControl.setValue(null); //
	 *         textControl.assertHasErrorWithText(
	 *         "Input is needed for field \"TextControl1\""); //
	 *         editor.assertHasErrors(1);
	 * 
	 *         String text = UUID.uuid();
	 * 
	 *         textControl.setValue(text); // textControl.assertHasNoErrors();
	 *         // editor.assertHasErrors(0);
	 * 
	 *         editor.save();
	 * 
	 *         // editor.assertTitle("Dictionary1 " + text);
	 * 
	 *         // test natural key errors // editor =
	 *         openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);
	 * 
	 *         // text control // textControl = //
	 *         editor.getTextControlTest(MangoDemoDictionaryModel
	 *         .TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1); //
	 *         textControl.setValue(text); // editor.save(); //
	 *         textControl.assertHasErrors(); //
	 *         textControl.assertHasErrorWithText("Duplicate value");
	 * 
	 *         runAsyncTests(); }
	 */
}
