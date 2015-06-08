package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.modules.dictionary.model.VOMetaModelProvider;
import io.pelle.mango.demo.client.MangoDemoClientConfiguration;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.demo.client.showcase.CompanyVO;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity4VO;
import io.pelle.mango.demo.client.test.Entity5VO;
import io.pelle.mango.demo.client.test.ValueObject1;

import org.junit.Test;

public class ValueObjectModelGeneratorTest {

	@Test
	public void testValueObject1String() {
		ValueObject1 valueObject1 = new ValueObject1();
		valueObject1.setString1("xxx");
		assertEquals("xxx", valueObject1.getString1());
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
	public void testEntity2VOHasNoNaturalKey() {
		Entity2VO entity2VO = new Entity2VO();

		assertEquals(entity2VO.getNaturalKey(), entity2VO.toString());
		assertFalse(entity2VO.hasNaturalKey());
	}

	@Test
	public void testCompanyHierarchicalAttributeDescriptors() {
		assertEquals(CompanyVO.PARENTCLASSNAME.getAttributeName(), "parentClassName");
		assertEquals(CompanyVO.PARENTID.getAttributeName(), "parentId");
		assertEquals(CompanyVO.PARENT.getAttributeName(), "parent");
		assertEquals(CompanyVO.HASCHILDREN.getAttributeName(), "hasChildren");
	}

	@Test
	public void testValueObjectMetaModelProvider() {

		MangoDemoClientConfiguration.registerAll();

		assertEquals("Entity2 Label", VOMetaModelProvider.getValueObjectEntityDescriptor(Entity2VO.class.getName()).getLabel());
		assertEquals("Entity2 Labels", VOMetaModelProvider.getValueObjectEntityDescriptor(Entity2VO.class.getName()).getPluralLabel());

		assertEquals("Entity2 Label", VOMetaModelProvider.getEntityDescriptor(MangoDemoDictionaryModel.DEMO_DICTIONARY2).getLabel());
	}
}
