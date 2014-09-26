package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import org.junit.Test;

public class DictionaryModelGeneratorTest {

	@Test
	public void testDictionaryName() {
		assertEquals("Testdictionary1", MangoDemoDictionaryModel.TESTDICTIONARY1.getName());
	}

	@Test
	public void testStringDatatype1GetAttributePath() {
		assertEquals("stringDatatype1", MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.getAttributePath());
	}

	@Test
	public void testNauralKeyIsMandatory() {
		assertTrue(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.isMandatory());
	}

	@Test
	public void testLabelInheritanceFromDatatype() {
		assertEquals("Boolean1", MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1.getLabel());
	}

}
