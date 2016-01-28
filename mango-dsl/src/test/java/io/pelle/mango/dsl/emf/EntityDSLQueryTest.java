package io.pelle.mango.dsl.emf;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityDataType;
import io.pelle.mango.dsl.mango.EntityEntityAttribute;
import io.pelle.mango.dsl.mango.impl.MangoFactoryImpl;
import io.pelle.mango.dsl.query.EntityQuery;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class EntityDSLQueryTest {

	private static Entity entity1;

	private static Entity entity2;

	private static Entity entity3;

	@BeforeClass
	public static void initTestModel() {
		entity1 = MangoFactoryImpl.eINSTANCE.createEntity();
		entity1.setName("entity1");

		entity2 = MangoFactoryImpl.eINSTANCE.createEntity();
		entity2.setName("entity2");

		entity3 = MangoFactoryImpl.eINSTANCE.createEntity();
		entity3.setName("entity3");

		// EntityAttributeType:
		// EntityDataType | Entity;
		EntityEntityAttribute entity2Attribute = MangoFactoryImpl.eINSTANCE.createEntityEntityAttribute();
		EntityDataType entity2DataType = MangoFactoryImpl.eINSTANCE.createEntityDataType();
		entity2DataType.setEntity(entity2);
		entity2Attribute.setType(entity2DataType);
		entity1.getAttributes().add(entity2Attribute);

		EntityEntityAttribute entity3Attribute = MangoFactoryImpl.eINSTANCE.createEntityEntityAttribute();
		entity3Attribute.setType(entity3);
		entity1.getAttributes().add(entity3Attribute);

	}

	@Test
	public void testGetReferencedEntities() {

		List<Entity> referencedEntities = EntityQuery.createQuery(entity1).getReferencedEntities();
		assertEquals(2, referencedEntities.size());

		assertEquals("entity2", referencedEntities.get(0).getName());
		assertEquals("entity3", referencedEntities.get(1).getName());
	}
}
