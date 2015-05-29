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
	public void testValueObject1Entity5() {
		Entity5VO entity5vo = new Entity5VO();
		ValueObject1 valueObject1 = new ValueObject1();
		valueObject1.setEntity5(entity5vo);
		assertEquals(entity5vo, valueObject1.getEntity5());
	}

	@Test
	public void testGetNaturalKey() {

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("DE");

		assertTrue(countryVO.hasNaturalKey());
		assertEquals("DE", countryVO.getNaturalKey());
	}

	@Test
	public void testHasNaturalKey() {
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
