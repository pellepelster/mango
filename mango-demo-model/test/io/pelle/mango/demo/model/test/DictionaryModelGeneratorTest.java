package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.test.client.ENUMERATION1;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import org.junit.Test;

public class DictionaryModelGeneratorTest {

	@Test
	public void testDictionaryName() {
		assertEquals("Testdictionary1", MangoDemoDictionaryModel.TESTDICTIONARY1.getName());
	}

	@Test
	public void testMaxLengthInheritanceFromDatatype() {
		assertEquals(42, MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.getMaxLength());
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

	@Test
	public void testControlGroup() {
		assertEquals("Textcontrol1", MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.CONTROL_GROUP1.TEXTCONTROL1.getName());
	}

	@Test
	public void testEnumerationValueParser() {
		MangoDemoClientConfiguration.registerAll();
		assertEquals(null, DictionaryModelProvider.getEnumerationValue(ENUMERATION1.class.getName(), null));
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, DictionaryModelProvider.getEnumerationValue(ENUMERATION1.class.getName(), ENUMERATION1.ENUMERATIONVALUE1.toString()));
	}
}
