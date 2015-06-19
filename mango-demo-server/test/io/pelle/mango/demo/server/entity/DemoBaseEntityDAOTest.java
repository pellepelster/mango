package io.pelle.mango.demo.server.entity;

import static io.pelle.mango.client.base.vo.query.CountQuery.countFrom;
import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.demo.server.test.Entity2;
import io.pelle.mango.demo.server.test.Entity3;
import io.pelle.mango.demo.server.test.Entity4;
import io.pelle.mango.demo.server.test.Entity5;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

public class DemoBaseEntityDAOTest extends BaseDemoTest {

	@Autowired
	private DataSource dataSource;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Before
	public void initTestData() {
		baseEntityDAO.deleteAll(Entity1.class);
		baseEntityDAO.deleteAll(Entity2.class);
		baseEntityDAO.deleteAll(Entity3.class);
		baseEntityDAO.deleteAll(Entity4.class);
		baseEntityDAO.deleteAll(Entity5.class);
	}

	@Test
	public void testSimpleCeate() {
		Entity1 newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("xxx");
		Entity1 createdEntity1 = baseEntityDAO.create(newEntity1);
		assertEquals(newEntity1.getStringDatatype1(), createdEntity1.getStringDatatype1());
		Entity1 readEntity1 = baseEntityDAO.read(createdEntity1.getId(), Entity1.class);
		assertEquals(newEntity1.getStringDatatype1(), readEntity1.getStringDatatype1());
	}

	@Test
	public void testListSave() {
		Entity2 entity2 = new Entity2();

		Entity3 entity31 = new Entity3();
		entity31.setStringDatatype3("entity31");
		entity2.getEntity3Datatypes().add(entity31);

		entity31 = baseEntityDAO.save(entity31);

		assertEquals(1, entity2.getEntity3Datatypes().size());
		assertEquals("entity31", entity2.getEntity3Datatypes().get(0).getStringDatatype3());

		Entity3 entity32 = new Entity3();
		entity32.setStringDatatype3("entity32");
		entity2.getEntity3Datatypes().add(entity32);

		entity31 = baseEntityDAO.save(entity31);

		assertEquals(2, entity2.getEntity3Datatypes().size());
		assertEquals("entity31", entity2.getEntity3Datatypes().get(0).getStringDatatype3());
		assertEquals("entity32", entity2.getEntity3Datatypes().get(1).getStringDatatype3());
	}

	@Test
	public void testSimpleSave() {

		Entity1 newEntity = new Entity1();
		newEntity.setStringDatatype1("xxx");
		Entity1 createdEntity = baseEntityDAO.create(newEntity);
		createdEntity = baseEntityDAO.read(createdEntity.getId(), Entity1.class);

		createdEntity.setStringDatatype1("yyy");
		baseEntityDAO.save(createdEntity);

		Entity1 savedVO = baseEntityDAO.read(createdEntity.getId(), Entity1.class);

		assertEquals(createdEntity.getStringDatatype1(), savedVO.getStringDatatype1());
	}

	@Test
	public void testBinaryCreateAndSave() {

		byte[] binary1 = new byte[] { 0xa, 0xb, 0xc };
		byte[] binary2 = new byte[] { 0x1, 0x2, 0x3 };

		Entity3 newEntity = new Entity3();
		newEntity.setBinaryDatatype1(binary1);

		Entity3 createdEntity = baseEntityDAO.create(newEntity);
		assertArrayEquals(binary1, createdEntity.getBinaryDatatype1());

		Entity3 readEntity = baseEntityDAO.read(createdEntity.getId(), Entity3.class);
		assertArrayEquals(binary1, readEntity.getBinaryDatatype1());

		readEntity.setBinaryDatatype1(binary2);
		baseEntityDAO.save(readEntity);

		readEntity = baseEntityDAO.read(createdEntity.getId(), Entity3.class);

		assertArrayEquals(binary2, readEntity.getBinaryDatatype1());
	}

	@Test
	public void testFilterAll() {

		Entity1 newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("xxx");
		baseEntityDAO.create(newEntity1);

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class));
		assertEquals(1, entity1s.size());
		assertNull(entity1s.get(0).getEntity2Datatype());
	}

	@Test
	public void testReadWithSelectQuery() {

		Entity1 newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("xxx");
		baseEntityDAO.create(newEntity1);

		Entity1 newEntity2 = new Entity1();
		newEntity2.setStringDatatype1("yyy");
		baseEntityDAO.create(newEntity2);

		Optional<Entity1> readEntity = baseEntityDAO.read(selectFrom(Entity1.class));
		assertFalse(readEntity.isPresent());

		readEntity = baseEntityDAO.read(selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.eq("xxx")));
		assertTrue(readEntity.isPresent());

		assertEquals("xxx", readEntity.get().getStringDatatype1());
	}

	@Test
	@Ignore
	public void testFilterWithInheritedAttributeDescriptors() {

		Entity4 newEntity = new Entity4();
		newEntity.setStringDatatype4("xxx");
		newEntity.setStringDatatype3("yyy");
		baseEntityDAO.create(newEntity);

		Optional<Entity4> readEntity = baseEntityDAO.read(selectFrom(Entity4.class).where(Entity4.STRINGDATATYPE3.eq("yyy")));
		assertTrue(readEntity.isPresent());

		readEntity = baseEntityDAO.read(selectFrom(Entity4.class).where(Entity4.STRINGDATATYPE4.eq("xxx")));
		assertTrue(readEntity.isPresent());

	}

	@Test
	public void testFilterByStringEquals() {

		Entity1 newEntity = new Entity1();
		newEntity.setStringDatatype1("xxx");
		baseEntityDAO.create(newEntity);

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.eq("xxx")));
		assertEquals(1, entity1s.size());
		assertEquals("xxx", entity1s.get(0).getStringDatatype1());

		entity1s = baseEntityDAO.filter(selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.eq("yyy")));
		assertEquals(0, entity1s.size());
	}

	@Test
	public void testFilterByEntityEquals() {

		Entity1 newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("xxx");

		Entity2 newEntity21 = new Entity2();
		newEntity21.setStringDatatype2("yyy");
		newEntity21 = baseEntityDAO.create(newEntity21);
		newEntity1.setEntity2Datatype(newEntity21);

		baseEntityDAO.create(newEntity1);

		newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("aaa");

		Entity2 newEntity22 = new Entity2();
		newEntity22.setStringDatatype2("bbb");
		newEntity22 = baseEntityDAO.create(newEntity22);
		newEntity1.setEntity2Datatype(newEntity22);

		baseEntityDAO.create(newEntity1);

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class).where(Entity1.ENTITY2DATATYPE.eq(newEntity21)));
		assertEquals(1, entity1s.size());
		assertEquals("xxx", entity1s.get(0).getStringDatatype1());
		assertEquals("yyy", entity1s.get(0).getEntity2Datatype().getStringDatatype2());

		entity1s = baseEntityDAO.filter(selectFrom(Entity1.class).where(Entity1.ENTITY2DATATYPE.eq(newEntity22)));
		assertEquals(1, entity1s.size());
		assertEquals("aaa", entity1s.get(0).getStringDatatype1());
		assertEquals("bbb", entity1s.get(0).getEntity2Datatype().getStringDatatype2());

	}

	@Test
	public void testFilterByStringLike() {

		Entity1 newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("xxx");
		baseEntityDAO.create(newEntity1);

		Entity1 newEntity2 = new Entity1();
		newEntity2.setStringDatatype1("XXXXXX");
		baseEntityDAO.create(newEntity2);

		Entity1 newEntity3 = new Entity1();
		newEntity3.setStringDatatype1("xxxxxx");
		baseEntityDAO.create(newEntity3);

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.like("xxx")));
		assertEquals(2, entity1s.size());
		assertEquals("xxx", entity1s.get(0).getStringDatatype1());
		assertEquals("xxxxxx", entity1s.get(1).getStringDatatype1());
	}

	@Test
	public void testFilterByStringLikeCaseInsensitive() {

		Entity1 newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("xxx");
		baseEntityDAO.create(newEntity1);

		Entity1 newEntity2 = new Entity1();
		newEntity2.setStringDatatype1("XXXXXX");
		baseEntityDAO.create(newEntity2);

		Entity1 newEntity3 = new Entity1();
		newEntity3.setStringDatatype1("xxxxxx");
		baseEntityDAO.create(newEntity3);

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.caseInsensitiveLike("xxx")));
		assertEquals(3, entity1s.size());
		assertEquals("xxx", entity1s.get(0).getStringDatatype1());
		assertEquals("XXXXXX", entity1s.get(1).getStringDatatype1());
		assertEquals("xxxxxx", entity1s.get(2).getStringDatatype1());
	}

	@Test
	public void testFilterOR() {

		for (int i = 0; i < 10; i++) {
			Entity1 newEntity = new Entity1();
			newEntity.setStringDatatype1(Integer.toString(i));
			baseEntityDAO.create(newEntity);
		}

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.eq("1").or(Entity1.STRINGDATATYPE1.eq("2"))));
		assertEquals(2, entity1s.size());
	}

	@Test
	public void testFilterFirstLevelAttributesNotNull() {

		baseEntityDAO.deleteAll(Entity1.class);

		Entity1 newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("xxx");

		Entity2 newEntity2 = new Entity2();
		newEntity2.setStringDatatype2("yyy");
		newEntity1.setEntity2Datatype(newEntity2);

		baseEntityDAO.create(newEntity1);

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class));
		assertEquals(1, entity1s.size());
		assertNotNull(entity1s.get(0).getEntity2Datatype());
	}

	@Test
	public void testCreateStringList() {

		Entity1 newEntity = new Entity1();
		newEntity.getStringDatatype1List().add("xxx");

		Entity1 createdEntity = baseEntityDAO.create(newEntity);

		Entity1 readEntity = baseEntityDAO.read(createdEntity.getId(), Entity1.class);

		assertEquals("xxx", readEntity.getStringDatatype1List().get(0));

	}

	@Test
	public void testDeleteAll() {

		Entity1 newEntity = new Entity1();
		newEntity.setStringDatatype1("xxx");

		Entity1 createdEntity = baseEntityDAO.create(newEntity);

		baseEntityDAO.deleteAll(Entity1.class);

		Entity1 readEntity = baseEntityDAO.read(createdEntity.getId(), Entity1.class);

		assertNull(readEntity);
	}

	@Test
	public void testFilterPaging() {

		for (int i = 0; i < 10; i++) {
			Entity1 newEntity = new Entity1();
			newEntity.setStringDatatype1(Integer.toString(i));
			baseEntityDAO.create(newEntity);
		}

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class).setMaxResults(5));
		assertEquals(5, entity1s.size());

	}

	@Test
	public void testDelete() {

		for (int i = 0; i < 10; i++) {
			Entity1 newEntity = new Entity1();
			newEntity.setStringDatatype1(Integer.toString(i));
			baseEntityDAO.create(newEntity);
		}

		List<Entity1> entity1s = baseEntityDAO.filter(selectFrom(Entity1.class));
		assertEquals(10, entity1s.size());

		baseEntityDAO.delete(entity1s.get(0));

		entity1s = baseEntityDAO.filter(selectFrom(Entity1.class));
		assertEquals(9, entity1s.size());

	}

	@Test
	public void testCount() {

		for (int i = 0; i < 10; i++) {
			Entity1 newEntity = new Entity1();
			newEntity.setStringDatatype1(Integer.toString(i));
			baseEntityDAO.create(newEntity);
		}

		assertEquals(10, baseEntityDAO.count(countFrom(Entity1.class)));
		assertEquals(1, baseEntityDAO.count(countFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.eq("1"))));

	}

	@Test
	public void testGetByNaturalKey() {

		Entity1 newVO = new Entity1();
		newVO.setStringDatatype1("xxx");
		baseEntityDAO.create(newVO);

		newVO = new Entity1();
		newVO.setStringDatatype1("yyy");
		baseEntityDAO.create(newVO);

		assertFalse(baseEntityDAO.getByNaturalKey(Entity1.class, "aaa").isPresent());
		assertEquals("xxx", baseEntityDAO.getByNaturalKey(Entity1.class, "xxx").get().getStringDatatype1());

	}

	// @Test
	// public void testFilterSecondLevelAttributes() throws SQLException {
	//
	// int id = 999;
	//
	// PreparedStatement q =
	// dataSource.getConnection().prepareStatement("insert into entity3 (entity3_id, entity3_string3) values (?, ?)");
	// q.setInt(1, id);
	// q.setString(2, "zzz");
	// q.executeUpdate();
	//
	// q =
	// dataSource.getConnection().prepareStatement("insert into entity2 (entity2_id, entity2_string1) values (?, ?)");
	// q.setInt(1, id);
	// q.setString(2, "yyy");
	// q.executeUpdate();
	//
	// q =
	// dataSource.getConnection().prepareStatement("insert into entity1 (entity1_id, entity1_string1, entity2_entity2_id) values (?, ?, ?)");
	// q.setInt(1, id);
	// q.setString(2, "xxx");
	// q.setInt(3, id);
	// q.executeUpdate();
	//
	// q =
	// dataSource.getConnection().prepareStatement("insert into entity2_entity3 (entity2_entity2_id, entity3s_entity3_id) values (?, ?)");
	// q.setInt(1, id);
	// q.setInt(2, id);
	// q.executeUpdate();
	//
	// List<Entity1> entity1s =
	// baseEntityDao.filter(selectFrom(Entity1.class).join(Entity1.ENTITY2));
	// assertEquals(1, entity1s.size());
	// assertNotNull(entity1s.get(0).getEntity2());
	// }

	/*
	 * 
	 * 
	 * Hibernate: insert into entity2_entity3 (entity2_entity2_id,
	 * entity3s_entity3_id) values (?, ?)
	 */
	// @Test
	// public void testFilterSecondLevelAttributes() {
	//
	// entityManager.createNativeQuery(sqlString)
	// Entity1 newEntity1 = new Entity1();
	// newEntity1.setString1("xxx");
	//
	// Entity2 newEntity2 = new Entity2();
	// newEntity2.setString1("yyy");
	// newEntity1.setEntity2(newEntity2);
	//
	//
	// Entity3 newEntity3 = new Entity3();
	// newEntity3.setString3("zzz");
	// newEntity2.getEntity3s().add(newEntity3);
	//
	// baseEntityDao.create(newEntity1);
	//
	// List<Entity1> entity1s =
	// baseEntityDao.filter(selectQuery(Entity1.class).from(Entity1.class));
	// assertEquals(1, entity1s.size());
	// assertNull(entity1s.get(0).getEntity2());
	// }

}
