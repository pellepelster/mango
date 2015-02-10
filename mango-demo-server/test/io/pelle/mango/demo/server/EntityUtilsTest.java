package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.db.test.mockup.EntityVOMapper;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.showcase.Customer;
import io.pelle.mango.server.entity.EntityUtils;
import io.pelle.mango.server.entity.EntityUtils.ReferenceTree;

import org.junit.Test;

public class EntityUtilsTest extends BaseDemoTest {

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
		assertEquals("xxx", EntityUtils.createSelectQuery(CountryVO.class, "name == 'xxx'").getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCreateSimpleExpression() {
		assertEquals("SELECT x0 FROM CountryVO x0 WHERE x0.countryName = 'xxx'", EntityUtils.createSelectQuery(CountryVO.class, "countryName == 'xxx'").getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCreateAndExpression() {
		assertEquals("SELECT x0 FROM CountryVO x0 WHERE (x0.countryName = 'xxx' AND x0.countryName = 'yyy')", EntityUtils.createSelectQuery(CountryVO.class, "countryName == 'xxx' && countryName == 'yyy'").getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCreateExpressionBrackets() {
		assertEquals("SELECT x0 FROM CountryVO x0 WHERE (x0.countryName = 'xxx' OR (x0.countryName = 'zzz' AND x0.countryName = 'uuu') )",
				EntityUtils.createSelectQuery(CountryVO.class, "countryName == 'xxx' || (countryName == 'zzz' && countryName == 'uuu')").getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCreateOrExpression() {
		assertEquals("SELECT x0 FROM CountryVO x0 WHERE (x0.countryName = 'xxx' OR x0.countryName = 'yyy')", EntityUtils.createSelectQuery(CountryVO.class, "countryName == 'xxx' || countryName == 'yyy'").getJPQL(EntityVOMapper.INSTANCE));
	}

}
