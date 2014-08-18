package io.pelle.mango.demo.server;

import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.query.ServerSelectQuery;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.test.client.Entity1VO;

import org.junit.Test;

public class JPQLTest extends BaseDemoTest {


	@Test
	public void testSelectAll() {
		SelectQuery<Entity1VO> query = selectFrom(Entity1VO.class);
		assertEquals("SELECT x0 FROM Entity1 x0", ServerSelectQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}


}
