package io.pelle.mango.client.base.test;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.util.XPathUtil;

import org.junit.Test;

public class XPathUtilTest {

	@Test
	public void testCombine() {
		assertEquals("aaa/bbb", XPathUtil.combine("aaa/", "/bbb"));
		assertEquals("aaa", XPathUtil.combine("aaa", null));
		assertEquals("bbb", XPathUtil.combine(null, "bbb"));
		assertEquals("aaa/bbb", XPathUtil.combine("aaa", "bbb"));
	}
}
