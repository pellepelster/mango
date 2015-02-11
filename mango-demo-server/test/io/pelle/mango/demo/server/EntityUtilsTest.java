package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.QueryUtils;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.showcase.Customer;
import io.pelle.mango.server.entity.EntityUtils;
import io.pelle.mango.server.entity.EntityUtils.ReferenceTree;

import org.junit.Test;

public class EntityUtilsTest extends BaseDemoTest {

	@Test
	public void testParseOperatorFromText() {
		assertEquals(ComparisonOperator.LIKE_NO_CASE, QueryUtils.parseOperatorFromText("abc%"));
	}

	@Test
	public void testSimpleParseReferenceTree() {

		ReferenceTree referenceTree = EntityUtils.parseReferenceTree(Customer.class, "title,firstName,LastName");
		assertEquals(3, referenceTree.getAttributePaths().size());
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.TITLE));
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.FIRSTNAME));
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.LASTNAME));

		referenceTree = EntityUtils.parseReferenceTree(Customer.class, "titlE,firstName,LASTName");
		assertEquals(3, referenceTree.getAttributePaths().size());
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.TITLE));
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.FIRSTNAME));
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.LASTNAME));
	}

	@Test
	public void testDeepParseReferenceTree() {

		ReferenceTree referenceTree = EntityUtils.parseReferenceTree(Customer.class, "title,firstName,country[countryname]");
		assertEquals(3, referenceTree.getAttributePaths().size());
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.TITLE));
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.FIRSTNAME));
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Customer.COUNTRY));

		referenceTree = referenceTree.getAttributePaths().get(Customer.COUNTRY);
		assertEquals(1, referenceTree.getAttributePaths().size());
		assertTrue(referenceTree.getAttributePaths().keySet().contains(Country.COUNTRYNAME));
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidAttributePath() {
		EntityUtils.parseReferenceTree(Customer.class, "aaa");
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidAttributePathNonVOType() {
		EntityUtils.parseReferenceTree(Customer.class, "title[name]");
	}

	@Test(expected = RuntimeException.class)
	public void testCreateInvalidExpression() {
		assertEquals("xxx", EntityUtils.createServerSelectQuery(CountryVO.class, "name == 'xxx'").getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testCreateQueryBeginsWith() {
		assertEquals("SELECT x0 FROM Country x0 WHERE LOWER(x0.countryName) LIKE LOWER('x%')", EntityUtils.createServerSelectQuery(CountryVO.class, "countryName matches 'x%'").getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testCreateQuerySimpleExpressionVO() {
		assertEquals("SELECT x0 FROM Country x0 WHERE x0.countryName = 'xxx'", EntityUtils.createServerSelectQuery(CountryVO.class, "countryName == 'xxx'").getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testCreateQuerySimpleExpressionEntity() {
		assertEquals("SELECT x0 FROM Country x0 WHERE x0.countryName = 'xxx'", EntityUtils.createServerSelectQuery(Country.class, "countryName == 'xxx'").getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testCreateQueryAndExpression() {
		assertEquals("SELECT x0 FROM Country x0 WHERE (x0.countryName = 'xxx' AND x0.countryName = 'yyy')",
				EntityUtils.createServerSelectQuery(CountryVO.class, "countryName == 'xxx' && countryName == 'yyy'").getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testCreateQueryEmpty() {
		assertEquals("SELECT x0 FROM Country x0", EntityUtils.createServerSelectQuery(CountryVO.class, "").getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testCreateQueryExpressionBrackets() {
		assertEquals("SELECT x0 FROM Country x0 WHERE (x0.countryName = 'xxx' OR (x0.countryName = 'zzz' AND x0.countryName = 'uuu') )",
				EntityUtils.createServerSelectQuery(CountryVO.class, "countryName == 'xxx' || (countryName == 'zzz' && countryName == 'uuu')").getJPQL(EntityVOMapper.getInstance()));
	}

	@Test
	public void testCreateQueryOrExpression() {
		assertEquals("SELECT x0 FROM Country x0 WHERE (x0.countryName = 'xxx' OR x0.countryName = 'yyy')",
				EntityUtils.createServerSelectQuery(CountryVO.class, "countryName == 'xxx' || countryName == 'yyy'").getJPQL(EntityVOMapper.getInstance()));
	}

}
