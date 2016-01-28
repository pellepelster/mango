package io.pelle.mango.client.gwt;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.gwt.utils.HtmlUtils;

import org.junit.Test;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class HtmlUtilsTest {

	@Test
	public void testStrong() {

		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts("aa", "bb", sb);
		assertEquals("bb", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts("aa", "aa", sb);
		assertEquals("<strong>aa</strong>", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts("aa", "aabb", sb);
		assertEquals("<strong>aa</strong>bb", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts("aa", "aabbaa", sb);
		assertEquals("<strong>aa</strong>bbaa", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts("aa", "bbaa", sb);
		assertEquals("bb<strong>aa</strong>", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts("aa", "", sb);
		assertEquals("", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts("aa", null, sb);
		assertEquals("", sb.toSafeHtml().asString());

		sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts("aa", "Aabb", sb);
		assertEquals("<strong>Aa</strong>bb", sb.toSafeHtml().asString());

	}
}
