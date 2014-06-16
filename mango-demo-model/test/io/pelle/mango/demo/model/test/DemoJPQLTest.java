package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.test.mockup.EntityVOMapper;
import io.pelle.mango.test.Entity1;

import org.junit.Test;

public class DemoJPQLTest extends BaseDemoModelTest {

	@Test
	public void testSimpleCeate() {

		Entity1 newEntity1 = new Entity1();
		newEntity1.setStringDatatype1("xxx");

		SelectQuery<Entity1> query = SelectQuery.selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.eq("abc"));
		assertEquals("SELECT x0 FROM DBTest1 x0 LEFT JOIN x0.test2s x1 LEFT JOIN x1.test3 x2", query.getJPQL(EntityVOMapper.INSTANCE));

	}

}
