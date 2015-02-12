package io.pelle.mango.demo.model.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.pelle.mango.client.api.WebHookVO;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CountryVO;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

@WebAppConfiguration
public class WebHookApiTest extends BaseDemoModelTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(8888));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testListWebhooks() throws Exception {
		baseEntityService.deleteAll(WebHookVO.class.getName());
		mockMvc.perform(get("/api/entity/country/webhooks")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").value(Matchers.hasSize(0)));
	}

	@Test
	public void testCreateWebhook() throws Exception {
		baseEntityService.deleteAll(WebHookVO.class.getName());

		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("abc"));
		mockMvc.perform(get("/api/entity/country/webhooks")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").value(Matchers.hasSize(1)));
	}

	@Test
	@Ignore
	public void testDeleteWebhook() throws Exception {
		baseEntityService.deleteAll(WebHookVO.class.getName());

		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("abc"));
		mockMvc.perform(get("/api/entity/country/webhooks")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").value(Matchers.hasSize(1)));
		mockMvc.perform(delete("/api/entity/country/webhooks/{hookName}", "abc")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.").value(true));
		mockMvc.perform(get("/api/entity/country/webhooks")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").value(Matchers.hasSize(0)));
	}

	@Test
	public void testCallWebhook() throws Exception {
		baseEntityService.deleteAll(WebHookVO.class.getName());

		stubFor(post(urlEqualTo("/countryhook")).willReturn(aResponse().withStatus(200)));

		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\", \"url\": \"http://localhost:8888/countryhook\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("abc"));

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AAA");
		baseEntityService.create(countryVO);

		verify(postRequestedFor(urlMatching("/countryhook")).withRequestBody(equalToJson("{ payload: { countryIsoCode2: \"AA\" } }", JSONCompareMode.LENIENT)));
	}
}