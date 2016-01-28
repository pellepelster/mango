package io.pelle.mango.demo.server.api;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.pelle.mango.demo.server.MangoDemoTestApplicationContext;
import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.MangoWebMvcApplicationContext;

@WebAppConfiguration
@ContextConfiguration(classes = { MangoWebMvcApplicationContext.class, MangoDemoTestApplicationContext.class })
public class DemoRestServiceTest extends BaseDemoTest {

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
	public void testMethodWithBooleanParameterGetUrlParameter() throws Exception {
		mockMvc.perform(get("/restservice2/methodwithbooleanparameter?onOff=true")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("false"));
	}

	@Test
	public void testMethodWithBooleanParameterPostRequestJsonBody() throws Exception {
		mockMvc.perform(post("/restservice2/methodwithbooleanparameter").content("{ \"onOff\": false }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().string("true"));
	}

	@Test
	public void testMethodWithValueObjectParameter() throws Exception {

		mockMvc.perform(post("/restservice2/methodwithvalueobjectparameter").content("{ \"string2\": \"zzz\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.string1").value(Matchers.is("zzz")));
	}

}