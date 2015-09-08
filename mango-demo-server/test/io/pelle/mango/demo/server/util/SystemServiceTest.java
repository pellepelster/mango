package io.pelle.mango.demo.server.util;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.pelle.mango.demo.server.MangoDemoTestApplicationContext;
import io.pelle.mango.server.MangoWebMvcApplicationContext;

@WebAppConfiguration
@ContextConfiguration(classes = { MangoWebMvcApplicationContext.class, MangoDemoTestApplicationContext.class })
public class SystemServiceTest extends BaseDemoTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(8888));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}

	@Test
	public void testGetSystemInformation() throws Exception {
		mockMvc.perform(post("/systemservice/getsysteminformation").content("{ }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testGetDictionaryI18N() throws Exception {

		String content = mockMvc.perform(get("/systemservice/getdictionaryi18nscript?variableName=xxx")).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertTrue(content.trim().startsWith("var xxx = {"));
		assertTrue(content.trim().contains("employee_plurallabel = \"Employees\";"));
		assertTrue(content.trim().contains("mangouser_label = \"User\";"));
		assertTrue(content.trim().endsWith("};"));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.ACCEPT_LANGUAGE, Locale.GERMANY.toString());

		content = mockMvc.perform(get("/systemservice/getdictionaryi18nscript?variableName=xxx&locale=de_DE").headers(httpHeaders)).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertTrue(content.trim().startsWith("var xxx = {"));
		assertTrue(content.trim().contains("employee_plurallabel = \"Employees\";"));
		assertTrue(content.trim().contains("mangouser_label = \"User\";"));
		assertTrue(content.trim().endsWith("};"));

	}

}