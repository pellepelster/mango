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
import io.pelle.mango.client.web.test.MangoAsyncGwtTestCase;
import io.pelle.mango.client.web.test.MangoClientWebTest;
import io.pelle.mango.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.DictionarySearchModuleTestUIAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.TextControlTestAsyncHelper;
import io.pelle.mango.client.web.test.vo.Test1VO;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import org.junit.Ignore;
import org.junit.Test;

public class DemoClientDictionary1Test extends MangoAsyncGwtTestCase<Test1VO> {

	@Override
	public String getModuleName() {
		return "io.pelle.mango.demo.DemoClientTest";
	}

	@Test
	public void testSimpleCreateAndSearch() {
		
		MangoClientWebTest.getInstance();
		MangoDemoClientConfiguration.registerAll();

		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		String text = UUID.uuid();

		// text control
		TextControlTestAsyncHelper textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		textControl.setValue(text);
		editor.save();

		DictionarySearchModuleTestUIAsyncHelper<Test1VO> search = openSearch(MangoDemoDictionaryModel.TESTDICTIONARY1);
		search.execute();
		search.assertResultCount(1);

		runAsyncTests();
	}

	
	@Test
	@Ignore
	public void testTextControl() {
		
		MangoClientWebTest.getInstance();
		MangoDemoClientConfiguration.registerAll();

		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		// text control
		TextControlTestAsyncHelper textControl = editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		// textControl.assertMandatory();
		//
		// textControl.assertHasNoErrors();
		// textControl.setValue("xxx");
		//
		// textControl.assertHasNoErrors();
		// textControl.setValue(null);
		// textControl.assertHasErrorWithText("Input is needed for field \"TextControl1\"");
		// editor.assertHasErrors(1);

		String text = UUID.uuid();

		textControl.setValue(text);
		// textControl.assertHasNoErrors();
		// editor.assertHasErrors(0);

		editor.save();

		// editor.assertTitle("Dictionary1 " + text);

		// test natural key errors
		// editor = openEditor(MangoDemoDictionaryModel.TESTDICTIONARY1);

		// text control
		// textControl =
		// editor.getTextControlTest(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1);
		// textControl.setValue(text);
		// editor.save();
		// textControl.assertHasErrors();
		// textControl.assertHasErrorWithText("Duplicate value");

		runAsyncTests();
	}
}
