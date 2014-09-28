package io.pelle.mango.client.gwt;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.gwt.utils.HtmlUtils;

import org.junit.Test;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class HtmlUtilsTest {

	@Test
	public void testStrong() {

		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		HtmlUtils.strong("aa", "bb", sb);
		assertEquals("bb", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.strong("aa", "aa", sb);
		assertEquals("<strong>aa</strong>", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.strong("aa", "aabb", sb);
		assertEquals("<strong>aa</strong>bb", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.strong("aa", "bbaa", sb);
		assertEquals("bb<strong>aa</strong>", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.strong("aa", "", sb);
		assertEquals("", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.strong("aa", null, sb);
		assertEquals("", sb.toSafeHtml().asString());

	}
}
