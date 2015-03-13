package io.pelle.mango.demo.server.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.demo.server.test.IEntity1EntityDAO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Entity1EntityDAOTest extends BaseDemoTest {

	@Autowired
	private IEntity1EntityDAO entity1EntityDAO;

	@Test
	public void testEntity1DAOCreate() {

		Entity1 entity = new Entity1();
		entity = entity1EntityDAO.create(entity);

		assertEquals(1, entity1EntityDAO.count());
		assertFalse(entity.isNew());
	}

	@Test
	public void testEntity1DAODeleteById() {

		Entity1 entity = new Entity1();
		entity = entity1EntityDAO.create(entity);

		assertEquals(1, entity1EntityDAO.count());
		entity1EntityDAO.delete(entity.getId());
		assertEquals(0, entity1EntityDAO.count());
	}

}
