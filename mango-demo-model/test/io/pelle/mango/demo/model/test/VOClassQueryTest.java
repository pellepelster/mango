package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.db.voquery.AttributesDescriptorQuery;
import io.pelle.mango.db.voquery.VOClassQuery;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity4VO;
import io.pelle.mango.demo.client.test.Entity5VO;

public class VOClassQueryTest {

	@Test
	public void testAttributesDescriptorQuery() {

		AttributesDescriptorQuery<?> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors();
		assertEquals(24, attributesDescriptorQuery.getCount());
	}

	@Test
	public void testAttributesDescriptorQueryByType() {

		AttributesDescriptorQuery<StringAttributeDescriptor> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byType(StringAttributeDescriptor.class);

		assertEquals(6, attributesDescriptorQuery.getCount());
		assertEquals(String.class, attributesDescriptorQuery.iterator().next().getAttributeType());
		assertEquals(String.class, attributesDescriptorQuery.iterator().next().getAttributeType());
	}

	@Test
	public void testAttributesDescriptorQueryByName() {

		AttributesDescriptorQuery<StringAttributeDescriptor> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byName("stringDatatype1");
		assertEquals("stringDatatype1", attributesDescriptorQuery.getSingleResult().getAttributeName());

		attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byName("stringDATatype1");
		assertEquals("stringDatatype1", attributesDescriptorQuery.getSingleResult().getAttributeName());

	}

	@Test
	public void testQueryEntity1FileEntityDatatypes1() {

		AttributesDescriptorQuery attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byName("fileEntityDatatypes1");
		assertTrue(attributesDescriptorQuery.hasExactlyOne());

	}

	@Test
	public void testEntity5VOAttributesDescriptorQueryNaturalKeys() {

		AttributesDescriptorQuery<?> attributesDescriptorQuery = VOClassQuery.createQuery(Entity5VO.class).attributesDescriptors().naturalKeys();

		assertEquals(2, attributesDescriptorQuery.getCount());

		Iterator<IAttributeDescriptor> iterator = (Iterator<IAttributeDescriptor>) attributesDescriptorQuery.iterator();

		IAttributeDescriptor<?> attributeDescriptor1 = iterator.next();
		assertEquals(String.class, attributeDescriptor1.getAttributeType());
		assertEquals("string1", attributeDescriptor1.getAttributeName());

		IAttributeDescriptor<?> attributeDescriptor2 = iterator.next();
		assertEquals(Entity4VO.class, attributeDescriptor2.getAttributeType());
		assertEquals("entity4", attributeDescriptor2.getAttributeName());

	}

	@Test
	public void testAttributesDescriptorQueryNaturalKeys() {

		AttributesDescriptorQuery<?> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().naturalKeys();

		assertEquals(1, attributesDescriptorQuery.getCount());

		IAttributeDescriptor<?> attributeDescriptor = attributesDescriptorQuery.iterator().next();
		assertEquals(String.class, attributeDescriptor.getAttributeType());
		assertEquals("stringDatatype1", attributeDescriptor.getAttributeName());
	}

}
