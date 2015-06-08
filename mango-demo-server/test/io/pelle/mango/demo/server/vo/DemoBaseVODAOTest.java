package io.pelle.mango.demo.server.vo;

import static io.pelle.mango.client.base.vo.query.AggregateQuery.aggregateFrom;
import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity3VO;
import io.pelle.mango.demo.client.test.Entity4VO;
import io.pelle.mango.demo.server.BaseDemoTest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
		baseVODAO.deleteAll(Entity2VO.class);
		baseVODAO.deleteAll(Entity3VO.class);
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
	public void testGetByNaturalKey() {

		Entity1VO newVO = new Entity1VO();
		newVO.setStringDatatype1("xxx");
		baseVODAO.create(newVO);

		newVO = new Entity1VO();
		newVO.setStringDatatype1("yyy");
		baseVODAO.create(newVO);

		assertFalse(baseVODAO.getByNaturalKey(Entity1VO.class, "aaa").isPresent());
		assertEquals("xxx", baseVODAO.getByNaturalKey(Entity1VO.class, "xxx").get().getStringDatatype1());

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
		assertTrue(entity1s.get(0).getEntity2Datatype().getEntity3Datatypes().isEmpty());
	}

	@Test
	public void testSimpleFilterSecondLevel() {

		Entity1VO newVO1 = new Entity1VO();
		newVO1.setStringDatatype1("xxx");

		Entity2VO newVO2 = new Entity2VO();
		newVO2.setStringDatatype2("xxx");
		newVO1.setEntity2Datatype(newVO2);

		Entity3VO newVO3 = new Entity3VO();
		newVO3.setStringDatatype3("xxx");
		newVO2.getEntity3Datatypes().add(newVO3);

		baseVODAO.create(newVO1);

		List<Entity1VO> entity1s = baseVODAO.filter(selectFrom(Entity1VO.class));
		assertEquals(1, entity1s.size());
		assertNotNull(entity1s.get(0).getEntity2Datatype());
		assertTrue(entity1s.get(0).getEntity2Datatype().getEntity3Datatypes().isEmpty());

		entity1s = baseVODAO.filter(selectFrom(Entity1VO.class).join(Entity1VO.ENTITY2DATATYPE, Entity2VO.ENTITY3DATATYPES));
		assertEquals(1, entity1s.get(0).getEntity2Datatype().getEntity3Datatypes().size());

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

}
