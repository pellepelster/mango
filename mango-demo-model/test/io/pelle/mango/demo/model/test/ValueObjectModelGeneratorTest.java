package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.base.modules.dictionary.model.VOMetaModelProvider;
import io.pelle.mango.demo.client.MangoDemoClientConfiguration;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.client.showcase.EmployeeVO;
import io.pelle.mango.demo.client.showcase.ManagerVO;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity4VO;
import io.pelle.mango.demo.client.test.Entity5VO;
import io.pelle.mango.demo.client.test.ValueObject1;

public class ValueObjectModelGeneratorTest {

	@Test
	public void testValueObject1String() {
		ValueObject1 valueObject1 = new ValueObject1();
		valueObject1.setString1("xxx");
		assertEquals("xxx", valueObject1.getString1());
		
		assertEquals(String.class, ValueObject1.STRING1.getAttributeType());
	}

	@Test
	public void testEntity4VOGenericGetterInheritedAttributes() {
		Entity4VO entity4 = new Entity4VO();
		entity4.setStringDatatype3("xxx");
		assertEquals("xxx", entity4.get("stringDatatype3"));
	}

	@Test
	public void testEntity4VOGenericSetterInheritedAttributes() {
		Entity4VO entity4 = new Entity4VO();
		entity4.set("stringDatatype3", "xxx");
		assertEquals("xxx", entity4.getStringDatatype3());
	}

	@Test
	public void testEntity4VOInheritedAttributeDescriptors() {
		assertEquals("stringDatatype3", Entity4VO.STRINGDATATYPE3.getAttributeName());
		assertEquals(Entity4VO.ENTITY4, Entity4VO.STRINGDATATYPE3.getParent());
	}

	@Test
	public void testValueObject1Entity5() {
		Entity5VO entity5vo = new Entity5VO();
		ValueObject1 valueObject1 = new ValueObject1();
		valueObject1.setEntity5(entity5vo);
		assertEquals(entity5vo, valueObject1.getEntity5());
	}

	@Test
	public void testGetCountryNaturalKey() {

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("DE");

		assertTrue(countryVO.hasNaturalKey());
		assertEquals("DE", countryVO.getNaturalKey());
	}

	@Test
	public void testGetCountryNaturalKeyNull() {

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2(null);

		assertTrue(countryVO.hasNaturalKey());
		assertEquals("-", countryVO.getNaturalKey());
	}

	@Test
	public void testEntity1MangoModelFileReference() {
		assertEquals(FileVO.class, Entity1VO.FILEENTITYDATATYPE1.getAttributeType());
	}

	@Test
	public void testGetEntity5VOComposedNaturalKey() {

		Entity4VO entity4 = new Entity4VO();
		entity4.setStringDatatype4("def");

		Entity5VO entity5 = new Entity5VO();
		entity5.setString1("abc");
		entity5.setEntity4(entity4);

		assertTrue(entity5.hasNaturalKey());
		assertEquals("abc, def", entity5.getNaturalKey());
	}

	@Test
	public void testGetEntity5VOComposedNaturalKeyNull() {

		Entity5VO entity5 = new Entity5VO();
		entity5.setString1("abc");
		entity5.setEntity4(null);

		assertTrue(entity5.hasNaturalKey());
		assertEquals("abc, -", entity5.getNaturalKey());
	}

	@Test
	public void testEntity2VOHasNoNaturalKey() {
		Entity2VO entity2VO = new Entity2VO();

		assertEquals(entity2VO.getNaturalKey(), entity2VO.toString());
		assertFalse(entity2VO.hasNaturalKey());
	}

	@Test
	public void testEmployeGenericGetterSetterSetParent() {

		ManagerVO manager = new ManagerVO();

		EmployeeVO employee = new EmployeeVO();
		employee.set("parent", manager);

		assertEquals(manager, employee.get("parent"));

	}

	@Test
	public void testValueObjectMetaModelProvider() {

		MangoDemoClientConfiguration.registerAll();

		assertEquals("Entity2 Label", VOMetaModelProvider.getValueObjectEntityDescriptor(Entity2VO.class.getName()).getLabel());
		assertEquals("Entity2 Labels", VOMetaModelProvider.getValueObjectEntityDescriptor(Entity2VO.class.getName()).getPluralLabel());

		assertEquals("Entity2 Label", VOMetaModelProvider.getEntityDescriptor(MangoDemoDictionaryModel.DEMO_DICTIONARY2).getLabel());
	}
}
