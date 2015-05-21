package io.pelle.mango.demo.server.entity;

import static org.junit.Assert.assertFalse;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.demo.server.test.Entity4;
import io.pelle.mango.demo.server.test.IEntity4EntityDAO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Entity4EntityDAOTest extends BaseDemoTest {

	@Autowired
	private IEntity4EntityDAO entity4EntityDAO;

	@Test
	public void testEntity1DAOCreate() {

		Entity4 entity = new Entity4();
		entity = entity4EntityDAO.create(entity);

		assertFalse(entity.isNew());
	}

}
