package io.pelle.mango.dsl.emf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryComposite;
import io.pelle.mango.dsl.mango.DictionaryEditor;
import io.pelle.mango.dsl.mango.DictionaryTextControl;
import io.pelle.mango.dsl.mango.impl.MangoFactoryImpl;

import org.junit.BeforeClass;
import org.junit.Test;

public class EmfModelQueryTest {

	private static Dictionary dictionary;
	private static DictionaryEditor dictionaryEditor;
	private static DictionaryComposite dictionaryEditorRootComposite;

	@BeforeClass
	public static void initTestModel() {
		dictionary = MangoFactoryImpl.eINSTANCE.createDictionary();
		dictionaryEditor = MangoFactoryImpl.eINSTANCE.createDictionaryEditor();
		dictionary.setDictionaryeditor(dictionaryEditor);
		dictionaryEditorRootComposite = MangoFactoryImpl.eINSTANCE.createDictionaryComposite();
		dictionaryEditor.getContainercontents().add(dictionaryEditorRootComposite);
	}

	@Test
	public void testGetParentByType() {
		EObjectQuery<DictionaryEditor> result = EmfModelQuery.createEObjectQuery(dictionaryEditorRootComposite).getParentByType(DictionaryEditor.class);
		assertTrue(result.hasMatch());
		assertEquals(dictionaryEditor, result.getMatch());
	}

	@Test
	public void testGetParentByTypeNegative() {
		EObjectQuery<DictionaryTextControl> result = EmfModelQuery.createEObjectQuery(dictionaryEditorRootComposite).getParentByType(DictionaryTextControl.class);
		assertFalse(result.hasMatch());
	}

}
