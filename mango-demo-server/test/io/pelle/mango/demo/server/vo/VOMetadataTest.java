package io.pelle.mango.demo.server.vo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity3VO;
import io.pelle.mango.demo.server.BaseDemoTest;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VOMetadataTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testValueObjectMetadataCleanAfterConstruct() {
		Entity1VO entity1 = new Entity1VO();
		assertFalse(entity1.getMetadata().hasChanges());
	}

	@Test
	public void testValueObjectMetadataFirstLevelAttributes() {
		Entity1VO entity1 = new Entity1VO();
		baseEntityService.create(entity1);

		List<Entity1VO> result = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));

		assertTrue(result.get(0).getMetadata().isLoaded(Entity1VO.ENTITY2DATATYPE.getAttributeName()));
	}

	@Test
	public void testValueObjectMetadataSimpleTypeSetter() {
		Entity1VO entity1 = new Entity1VO();
		assertFalse(entity1.getMetadata().hasChanges());

		entity1.setStringDatatype1("xxx");
		assertTrue(entity1.getMetadata().hasChanges());
	}

	@Test
	public void testValueObjectMetadataSimpleTypeGenericSetter() {

		Entity1VO entity1 = new Entity1VO();
		assertFalse(entity1.getMetadata().hasChanges());

		entity1.set("stringDatatype1", "xxx");
		assertTrue(entity1.getMetadata().hasChanges());

	}

	@Test
	public void testValueObjectMetadataIBaseVOSetter() {
		Entity1VO entity1 = new Entity1VO();
		assertFalse(entity1.getMetadata().hasChanges());

		entity1.setEntity2Datatype(new Entity2VO());
		assertTrue(entity1.getMetadata().hasChanges());

	}

	@Test
	public void testValueObjectMetadataIBaseVOGenericSetter() {

		Entity1VO entity1 = new Entity1VO();
		assertFalse(entity1.getMetadata().hasChanges());

		entity1.set("entity2Datatype", new Entity2VO());
		assertTrue(entity1.getMetadata().hasChanges());

	}

	@Test
	public void testValueObjectMetadataAddListSimpleType() {

		Entity1VO entity1 = new Entity1VO();

		assertFalse(entity1.getMetadata().hasChanges());

		entity1.getStringDatatype1List().add("xxx");

		assertTrue(entity1.getMetadata().hasChanges());
	}

	@Test
	public void testValueObjectMetadataAddListIBaseVO() {

		Entity2VO entity2 = new Entity2VO();

		assertFalse(entity2.getMetadata().hasChanges());

		entity2.getEntity3Datatypes().add(new Entity3VO());

		assertTrue(entity2.getMetadata().hasChanges());
	}
}
