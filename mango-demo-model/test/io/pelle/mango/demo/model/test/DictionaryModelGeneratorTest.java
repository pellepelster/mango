package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.test.client.ENUMERATION1;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class DictionaryModelGeneratorTest {

	@Test
	public void testDictionaryName() {
		assertEquals("TestDictionary1", MangoDemoDictionaryModel.TEST_DICTIONARY1.getName());
	}

	@BeforeClass
	public static void init() {
		MangoDemoClientConfiguration.registerAll();
	}

	@Test
	public void testMaxLengthInheritanceFromDatatype() {
		assertEquals(42, MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.getMaxLength());
	}

	@Test
	public void testStringDatatype1GetAttributePath() {
		assertEquals("stringDatatype1", MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.getAttributePath());
	}

	@Test
	public void testNauralKeyIsMandatory() {
		assertTrue(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.isMandatory());
	}

	@Test
	public void testLabelInheritanceFromDatatype() {
		assertEquals("Boolean1", MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1.getLabel());
	}

	@Test
	public void testControlGroup() {
		assertEquals("Textcontrol1", MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.CONTROL_GROUP1.TEXTCONTROL1.getName());
	}

	@Test
	public void testEnumerationValueConverterGetEnumerationValue() {
		assertEquals(null, DictionaryModelProvider.getEnumerationValue(ENUMERATION1.class.getName(), null));
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, DictionaryModelProvider.getEnumerationValue(ENUMERATION1.class.getName(), "ENUMERATIONVALUE1"));

	}

	@Test
	public void testEnumerationConverterGetEnumerationValues() {
		Map<String, String> enumerationValues = DictionaryModelProvider.getEnumerationValues(ENUMERATION1.class.getName());
		assertEquals(2, enumerationValues.size());

		assertEquals("ENUMERATIONVALUE1", enumerationValues.get("ENUMERATIONVALUE1"));
		assertEquals("Value2", enumerationValues.get("ENUMERATIONVALUE2"));
	}
}
