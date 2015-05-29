package io.pelle.mango.demo.server;

import static io.pelle.mango.client.base.vo.query.AggregateQuery.aggregateFrom;
import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.query.ServerAggregateQuery;
import io.pelle.mango.db.query.ServerSelectQuery;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.demo.client.test.Entity1VO;

import org.junit.Test;

public class JPQLTest extends BaseDemoTest {

	@Test
	public void testSelectAll() {
		SelectQuery<Entity1VO> query = selectFrom(Entity1VO.class);
		assertEquals("SELECT x0 FROM Entity1 x0", ServerSelectQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testAggegrate() {
		AggregateQuery<Entity1VO> query = aggregateFrom(Entity1VO.class);
		query.sum(Entity1VO.INTEGERDATATYPE1);
		assertEquals("SELECT SUM(x0.integerDatatype1) FROM Entity1 x0", ServerAggregateQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testStringEqualsNull() {
		SelectQuery<Entity1VO> query = selectFrom(Entity1VO.class).where(Entity1VO.STRINGDATATYPE1.eq(null));
		assertEquals("SELECT x0 FROM Entity1 x0 WHERE x0.stringDatatype1 IS NULL", ServerSelectQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testStringIsNull() {
		SelectQuery<Entity1VO> query = selectFrom(Entity1VO.class).where(Entity1VO.STRINGDATATYPE1.isNull());
		assertEquals("SELECT x0 FROM Entity1 x0 WHERE x0.stringDatatype1 IS NULL", ServerSelectQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testStringIsNotNull() {
		SelectQuery<Entity1VO> query = selectFrom(Entity1VO.class).where(Entity1VO.STRINGDATATYPE1.notNull());
		assertEquals("SELECT x0 FROM Entity1 x0 WHERE x0.stringDatatype1 IS NOT NULL", ServerSelectQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testIntegerEqualsNull() {
		SelectQuery<Entity1VO> query = selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.eq(null));
		assertEquals("SELECT x0 FROM Entity1 x0 WHERE x0.integerDatatype1 IS NULL", ServerSelectQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testIntegerIsNull() {
		SelectQuery<Entity1VO> query = selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.isNull());
		assertEquals("SELECT x0 FROM Entity1 x0 WHERE x0.integerDatatype1 IS NULL", ServerSelectQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testIntegerStringIsNotNull() {
		SelectQuery<Entity1VO> query = selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.notNull());
		assertEquals("SELECT x0 FROM Entity1 x0 WHERE x0.integerDatatype1 IS NOT NULL", ServerSelectQuery.adapt(query).getJPQL(EntityVOMapper.getInstance()));
	}

}
