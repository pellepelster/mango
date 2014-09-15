package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.server.Entity1;

import org.junit.Test;

public class EntityVOMapperTest extends BaseDemoModelTest {

	@Test
	public void testMappings() {
		assertEquals(Entity1VO.class, EntityVOMapper.getInstance().getMappedClass(Entity1.class));
	}

}
