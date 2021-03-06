package io.pelle.mango.demo.server.vo;

import static io.pelle.mango.client.base.vo.query.AggregateQuery.aggregateFrom;
import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity3VO;
import io.pelle.mango.demo.client.test.Entity4VO;
import io.pelle.mango.demo.server.util.BaseDemoTest;

public class DemoBaseVODAOTest extends BaseDemoTest {

	@Autowired
	private DataSource dataSource;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IBaseVODAO baseVODAO;

	@Before
	public void initTestData() {
		baseVODAO.deleteAll(Entity1VO.class);
		baseVODAO.deleteAll(Entity3VO.class);
		baseVODAO.deleteAll(Entity2VO.class);
		baseVODAO.deleteAll(Entity4VO.class);
	}

	@Test
	public void testSimpleCeate() {

		Entity1VO newVO1 = new Entity1VO();
		newVO1.setStringDatatype1("xxx");
		Entity1VO createdVO1 = baseVODAO.create(newVO1);

		assertEquals(newVO1.getStringDatatype1(), createdVO1.getStringDatatype1());

		Entity1VO readEntity1 = baseVODAO.read(createdVO1.getId(), Entity1VO.class);
		assertEquals(newVO1.getStringDatatype1(), readEntity1.getStringDatatype1());
	}

	@Test
	public void testSimpleSave() {

		Entity1VO newVO = new Entity1VO();
		newVO.setStringDatatype1("xxx");
		Entity1VO createdVO = baseVODAO.create(newVO);
		createdVO = baseVODAO.read(createdVO.getId(), Entity1VO.class);

		createdVO.setStringDatatype1("yyy");
		baseVODAO.save(createdVO);

		Entity1VO savedVO = baseVODAO.read(createdVO.getId(), Entity1VO.class);

		assertEquals(createdVO.getStringDatatype1(), savedVO.getStringDatatype1());
	}

	@Test
	public void testSimpleFilterFirstLevel() {

		Entity1VO newVO1 = new Entity1VO();
		newVO1.setStringDatatype1("xxx");

		Entity2VO newVO2 = new Entity2VO();
		newVO2.setStringDatatype2("xxx");
		newVO1.setEntity2Datatype(newVO2);

		baseVODAO.create(newVO1);

		List<Entity1VO> entity1s = baseVODAO.filter(selectFrom(Entity1VO.class));
		assertEquals(1, entity1s.size());
		assertNotNull(entity1s.get(0).getEntity2Datatype());

		boolean thrown = false;

		try {
			entity1s.get(0).getEntity2Datatype().getEntity3Datatypes();
		} catch (RuntimeException e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

	@Test
	public void testSimpleFilterSecondLevel() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("xxx");

		Entity2VO entity2 = new Entity2VO();
		entity2.setStringDatatype2("xxx");
		entity1.setEntity2Datatype(entity2);

		Entity3VO entity3 = new Entity3VO();
		entity3.setStringDatatype3("xxx");
		entity2.getEntity3Datatypes().add(entity3);

		baseVODAO.create(entity1);

		List<Entity1VO> entity1s = baseVODAO.filter(selectFrom(Entity1VO.class));

		assertEquals(1, entity1s.size());
		assertNotNull(entity1s.get(0).getEntity2Datatype());

		boolean thrown = false;
		try {
			entity1s.get(0).getEntity2Datatype().getEntity3Datatypes();
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		entity1s = baseVODAO.filter(selectFrom(Entity1VO.class).join(new IAttributeDescriptor<?>[] { Entity1VO.ENTITY2DATATYPE, Entity2VO.ENTITY3DATATYPES }));
		assertEquals(1, entity1s.get(0).getEntity2Datatype().getEntity3Datatypes().size());

	}

	@Test
	public void testVOMetataCleanAfterFilter() {

		baseVODAO.deleteAll(Entity1VO.class);

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("xxx");

		baseVODAO.create(entity1);

		Optional<Entity1VO> entity1Result = baseVODAO.read(selectFrom(Entity1VO.class));

		assertFalse(entity1Result.get().getMetadata().hasChanges());
	}

	@Test
	public void testVOMetataFistLevelIsLoadedNull() {

		baseVODAO.deleteAll(Entity1VO.class);

		Entity1VO entity1 = new Entity1VO();
		entity1.setEntity2Datatype(null);
		baseVODAO.create(entity1);

		Optional<Entity1VO> entity1Result = baseVODAO.read(selectFrom(Entity1VO.class));

		assertTrue(entity1Result.get().getMetadata().isLoaded(Entity1VO.ENTITY2DATATYPE.getAttributeName()));
		assertTrue(entity1Result.get().getMetadata().isLoaded(Entity1VO.STRINGDATATYPE1LIST.getAttributeName()));
	}

	@Test
	public void testVOMetataCleanAfterCreate() {

		baseVODAO.deleteAll(Entity1VO.class);

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("xxx");

		assertFalse(baseVODAO.create(entity1).getMetadata().hasChanges());
	}

	@Test
	public void testCheckForUnloadedAttributesBeforeSave() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("xxx");

		Entity2VO entity2 = new Entity2VO();
		entity2.setStringDatatype2("xxx");
		entity1.setEntity2Datatype(entity2);

		baseVODAO.create(entity1);

		Optional<Entity1VO> entity1Result = baseVODAO.read(selectFrom(Entity1VO.class));

		assertNotNull(entity1Result.get().getEntity2Datatype());
		entity1Result.get().getEntity2Datatype().setStringDatatype2("ttt");

		boolean thrown = false;
		try {
			baseVODAO.save(entity1Result.get());
		} catch (RuntimeException e) {
			e.printStackTrace();
			thrown = true;
		}
		assertTrue(thrown);

	}

	@Test
	public void testEntityVOInheritance() {

		Entity4VO newVO4 = new Entity4VO();
		newVO4.setStringDatatype3("inheritance3");
		newVO4.setStringDatatype4("inheritance4");

		baseVODAO.create(newVO4);

		List<Entity4VO> entity4s = baseVODAO.filter(selectFrom(Entity4VO.class));
		assertEquals(1, entity4s.size());
		assertEquals("inheritance3", entity4s.get(0).getStringDatatype3());
		assertEquals("inheritance4", entity4s.get(0).getStringDatatype4());

		List<Entity3VO> entity3s = baseVODAO.filter(selectFrom(Entity3VO.class));
		assertEquals(1, entity3s.size());
		assertEquals("inheritance3", entity3s.get(0).getStringDatatype3());

	}

	@Test
	public void testEntity3VOInheritance() {

		Entity3VO newVO3 = new Entity3VO();
		newVO3.setStringDatatype3("inheritance3");

		baseVODAO.create(newVO3);

		List<Entity3VO> entity3s = baseVODAO.filter(selectFrom(Entity3VO.class));
		assertEquals(1, entity3s.size());
		assertEquals("inheritance3", entity3s.get(0).getStringDatatype3());

	}

	@Test
	public void testSumAggregateQuery() {

		Entity1VO newVO1 = new Entity1VO();
		newVO1.setStringDatatype1("xxx");
		newVO1.setIntegerDatatype1(3);
		baseVODAO.create(newVO1);

		newVO1 = new Entity1VO();
		newVO1.setStringDatatype1("yyy");
		newVO1.setIntegerDatatype1(4);
		baseVODAO.create(newVO1);

		AggregateQuery<Entity1VO> query = aggregateFrom(Entity1VO.class).sum(Entity1VO.INTEGERDATATYPE1);

		long result = baseVODAO.aggregate(query);
		assertEquals(7, result);
	}

	@Test
	public void testGetByNaturalKey() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		baseVODAO.create(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("aab");
		baseVODAO.create(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("aac");
		baseVODAO.create(entity1);

		List<Entity1VO> result = baseVODAO.searchByNaturalKey(Entity1VO.class, "aaa");
		assertEquals(1, result.size());

		result = baseVODAO.searchByNaturalKey(Entity1VO.class, "aa");
		assertEquals(3, result.size());

		result = baseVODAO.searchByNaturalKey(Entity1VO.class, "aab");
		assertEquals(1, result.size());

		result = baseVODAO.searchByNaturalKey(Entity1VO.class, "xxx");
		assertEquals(0, result.size());
	}

}
