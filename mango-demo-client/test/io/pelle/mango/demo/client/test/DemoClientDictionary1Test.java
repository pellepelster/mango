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

public class DemoClientDictionary1Test extends MangoAsyncGwtTestHelper<Entity1VO> {

	@Override
	public String getModuleName() {
		return "io.pelle.mango.demo.DemoClientTest";
	}

	@Override
	protected void gwtSetUp() throws Exception {
		MangoClientWebTest.getInstance();
		MangoDemoClientConfiguration.registerAll();
	}

	@Test
	public void testSimpleCreateAndSearchOpenEditor(long id, String text) {

		DictionaryEditorModuleTestUIAsyncHelper<Entity1VO> editor = openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1, id);
		TextControlTestAsyncHelper textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl.assertValue(text);

		runAsyncTests();
	}

	@Test
	public void testEntity1CreateAndSearch() {

		DictionaryEditorModuleTestUIAsyncHelper<Entity1VO> editor = openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		final String text = UUID.uuid();

		// text control
		TextControlTestAsyncHelper textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl.setValue(text);
		editor.save();

		DictionarySearchModuleTestUIAsyncHelper<Entity1VO> search = openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1);
		textControl = search.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.TEXTCONTROL1);
		textControl.setValue(text);
		search.execute();
		search.assertResultCount(1);

		search.getResultList(new BaseErrorAsyncCallback<List<ITableRow<Entity1VO>>>() {
			@Override
			public void onSuccess(List<ITableRow<Entity1VO>> result) {
				testSimpleCreateAndSearchOpenEditor(result.get(0).getVO().getId(), text);
			}
		});

		runAsyncTests();
	}

	@Test
	public void testTextControlMandatory() {

		DictionaryEditorModuleTestUIAsyncHelper<Entity1VO> editor = openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		// text control
		TextControlTestAsyncHelper textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);

		// natural key field is always mandatory
		textControl.assertMandatory();

		String text = UUID.uuid();

		// test mandatory handling
		textControl.assertHasNoErrors();
		textControl.setValue(text);
		textControl.assertHasNoErrors();
		textControl.setValue("");
		textControl.assertHasErrorWithText("Input is needed for 'Textcontrol1'");

		editor.assertHasErrors(1);

		textControl.setValue(text);
		textControl.assertHasNoErrors();
		editor.assertHasErrors(0);
		editor.save();

		runAsyncTests();
	}

	@Test
	public void testDuplicateNaturalKey() {

		DictionaryEditorModuleTestUIAsyncHelper<Entity1VO> editor = openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		TextControlTestAsyncHelper textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);

		String text = UUID.uuid();
		textControl.setValue(text);
		editor.save();

		editor = openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl.setValue(text);
		editor.save();

		textControl.assertHasErrors();
		textControl.assertHasErrorWithText("The Testdictionary1 '" + text + "' already exists");

		runAsyncTests();
	}
}
