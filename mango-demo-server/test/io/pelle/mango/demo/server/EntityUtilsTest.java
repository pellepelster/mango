package io.pelle.mango.demo.server;

import static org.junit.Assert.*;
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

	@Test(expected=RuntimeException.class)
	public void testInvalidAttributePath() {
		EntityUtils.parseReferenceTree(Customer.class, "aaa");
	}
	
	@Test(expected=RuntimeException.class)
	public void testInvalidAttributePathNonVOType() {
		EntityUtils.parseReferenceTree(Customer.class, "title[name]");
	}
	
}
