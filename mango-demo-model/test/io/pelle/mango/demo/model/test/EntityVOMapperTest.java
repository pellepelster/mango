package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.server.test.Entity1;

import org.junit.Test;

public class EntityVOMapperTest extends BaseDemoModelTest {

	@Test
	public void testMappings() {
		assertEquals(Entity1VO.class, EntityVOMapper.getInstance().getMappedClass(Entity1.class));
	}

}
