package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.db.vos.Length;
import io.pelle.mango.db.voquery.AttributeDescriptorAnnotation;
import io.pelle.mango.db.voquery.AttributesDescriptorQuery;
import io.pelle.mango.db.voquery.VOClassQuery;
import io.pelle.mango.test.client.Entity1VO;

import java.util.List;

import org.junit.Test;

public class VOClassQueryTest {

	@Test
	public void testAttributesDescriptorQuery() {

		AttributesDescriptorQuery<?> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors();
		assertEquals(5, attributesDescriptorQuery.getCount());
	}

	@Test
	public void testAttributesDescriptorQueryFilterByType() {

		AttributesDescriptorQuery<String> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byType(String.class);

		assertEquals(1, attributesDescriptorQuery.getCount());
		assertEquals(String.class, attributesDescriptorQuery.getList().get(0).getAttributeType());
	}

	@Test
	public void testAttributesDescriptorByAnnotation() {

		List<AttributeDescriptorAnnotation<Length>> attributesDescriptorAnnotations = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byAnnotation(Length.class);

		assertEquals(2, attributesDescriptorAnnotations.size());
		assertEquals(String.class, attributesDescriptorAnnotations.get(0).getAttributeDescriptor().getAttributeType());
		assertEquals(42, attributesDescriptorAnnotations.get(0).getAnnotation().maxLength());

		assertEquals(List.class, attributesDescriptorAnnotations.get(1).getAttributeDescriptor().getAttributeType());
		assertEquals(String.class, attributesDescriptorAnnotations.get(1).getAttributeDescriptor().getListAttributeType());
		assertEquals(42, attributesDescriptorAnnotations.get(1).getAnnotation().maxLength());
	}

}
