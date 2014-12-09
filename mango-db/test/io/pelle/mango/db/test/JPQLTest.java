package io.pelle.mango.db.test;

import static io.pelle.mango.client.base.vo.query.BaseQuery.and;
import static io.pelle.mango.client.base.vo.query.BaseQuery.or;
import static io.pelle.mango.client.base.vo.query.CountQuery.countFrom;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.query.ServerCountQuery;
import io.pelle.mango.db.query.ServerSelectQuery;
import io.pelle.mango.db.test.mockup.EntityVOMapper;
import io.pelle.mango.db.test.mockup.entities.DBTest1;
import io.pelle.mango.db.test.mockup.entities.DBTest2;
import io.pelle.mango.db.test.mockup.entities.DBTest3;
import junit.framework.TestCase;

import org.junit.Test;

public class JPQLTest extends TestCase {

	private void assertQueryEquals(String expected, SelectQuery<?> selectQuery) {
		ServerSelectQuery<?> query = ServerSelectQuery.adapt(selectQuery);
		assertEquals(expected, query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testJoin() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).join(DBTest1.TEST2S);
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 LEFT JOIN x0.test2s x1", query);
	}

	@Test
	public void testOrderByDefault() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).orderBy(DBTest1.TESTSTRING);
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 ORDER BY testString ASC", query);
	}

	@Test
	public void testOrderByDefaultDescending() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).orderBy(DBTest1.TESTSTRING).descending();
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 ORDER BY testString DESC", query);
	}

	@Test
	public void testOrderByDefaultAscending() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).orderBy(DBTest1.TESTSTRING).ascending();
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 ORDER BY testString ASC", query);
	}

	@Test
	public void testJoin1() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).join(DBTest1.TEST2S, DBTest2.TEST3);
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 LEFT JOIN x0.test2s x1 LEFT JOIN x1.test3 x2", query);
	}

	@Test
	public void testSelectAll() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class);
		assertQueryEquals("SELECT x0 FROM DBTest1 x0", query);
	}

	@Test
	public void testCriteria() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1l));
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1", query);
	}

	@Test
	public void testNestedCriteria() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1l).and(DBTest1.TEST2S.path(DBTest2.TESTSTRING).eq("rrr")));
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 AND x0.test2s.testString = 'rrr'", query);
	}

	@Test
	public void testCriteriaAndOr() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1l).and(DBTest1.ID.eq(2l).or(DBTest1.ID.eq(3l))));
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 AND x0.id = 2 OR x0.id = 3", query);
	}

	@Test
	public void testCriteriaAndNestedOr() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1l).and(or(DBTest1.ID.eq(2l), DBTest1.ID.eq(3l))));
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 AND (x0.id = 2 OR x0.id = 3)", query);
	}

	@Test
	public void testCriteriaOrNestedAnd() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1l).or(and(DBTest1.ID.eq(2l), DBTest1.ID.eq(3l))));
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 OR (x0.id = 2 AND x0.id = 3)", query);
	}

	@Test
	public void testCriteriaOr() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1l).or(DBTest1.ID.eq(2l)));
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 OR x0.id = 2", query);
	}

	@Test
	public void testCriteriaMultipleOr() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).where(DBTest1.TESTSTRING.eq("xxx").or(DBTest1.TESTSTRING.eq("yyy")));
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.testString = 'xxx' OR x0.testString = 'yyy'", query);
	}

	@Test
	public void testCriteriaAnd() {
		SelectQuery<DBTest1> query = SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1l).and(DBTest1.ID.eq(2l)));
		assertQueryEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 AND x0.id = 2", query);
	}

	@Test
	public void testEntityCriteria() {
		DBTest3 test3 = new DBTest3();
		test3.setId(9);

		ServerSelectQuery<DBTest2> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest2.class).where(DBTest2.TEST3.eq(test3)));
		assertEquals("SELECT x0 FROM DBTest2 x0 WHERE x0.test3.id = 9", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testEntityCriteria1() {
		DBTest3 test3 = new DBTest3();
		test3.setId(9);

		SelectQuery<DBTest2> query = SelectQuery.selectFrom(DBTest2.class).where(DBTest2.TEST3.eq(test3));
		assertQueryEquals("SELECT x0 FROM DBTest2 x0 WHERE x0.test3.id = 9", query);
	}

	@Test
	public void testStringEquals() {
		SelectQuery<DBTest2> query = SelectQuery.selectFrom(DBTest2.class).where(DBTest2.TEST1.eq("aaa"));
		assertQueryEquals("SELECT x0 FROM DBTest2 x0 WHERE x0.test1 = 'aaa'", query);
	}

	@Test
	public void testStringEqualsIgnoreCase() {
		SelectQuery<DBTest2> query = SelectQuery.selectFrom(DBTest2.class).where(DBTest2.TEST1.eqIgnoreCase("aaa"));
		assertQueryEquals("SELECT x0 FROM DBTest2 x0 WHERE LOWER(x0.test1) = LOWER('aaa')", query);
	}

	@Test
	public void testSelectCount() {
		CountQuery<DBTest1> query = countFrom(DBTest1.class);
		assertEquals("SELECT COUNT(x0) FROM DBTest1 x0", ServerCountQuery.adapt(query).getJPQL(EntityVOMapper.INSTANCE));
	}

}
