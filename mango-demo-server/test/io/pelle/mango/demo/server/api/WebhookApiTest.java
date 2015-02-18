package io.pelle.mango.demo.server.api;

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
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.server.BaseDemoTest;

import org.hamcrest.Matchers;
import org.junit.Before;
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
public class WebhookApiTest extends BaseDemoTest {

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
		baseEntityService.deleteAll(WebhookVO.class.getName());
		mockMvc.perform(get("/api/entity/country/webhooks")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").value(Matchers.hasSize(0)));
	}

	@Test
	public void testCreateWebhookWithoutName() throws Exception {
		baseEntityService.deleteAll(WebhookVO.class.getName());
		mockMvc.perform(post("/api/entity/country/webhooks").content("{ }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.error").value("no name provided; no URL provided"));
	}

	@Test
	public void testCreateWebhookDuplicateName() throws Exception {
		baseEntityService.deleteAll(WebhookVO.class.getName());
		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\",\"url\": \"def\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("abc"));
		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\",\"url\": \"def\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.error").value("webhook with name 'abc' already registered"));
	}

	@Test
	public void testCreateWebhookWithoutUrl() throws Exception {
		baseEntityService.deleteAll(WebhookVO.class.getName());
		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.error").value("no URL provided"));
	}

	@Test
	public void testCreateWebhook() throws Exception {
		baseEntityService.deleteAll(WebhookVO.class.getName());

		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\",\"url\": \"def\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("abc"));
		mockMvc.perform(get("/api/entity/country/webhooks")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").value(Matchers.hasSize(1)));
	}

	@Test
	public void testDeleteWebhook() throws Exception {
		baseEntityService.deleteAll(WebhookVO.class.getName());

		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\", \"url\": \"abc\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("abc"));
		mockMvc.perform(get("/api/entity/country/webhooks")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").value(Matchers.hasSize(1)));
		mockMvc.perform(delete("/api/entity/country/webhooks/{hookName}", "abc")).andDo(print()).andExpect(status().isOk());
		mockMvc.perform(get("/api/entity/country/webhooks")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").value(Matchers.hasSize(0)));
	}

	@Test
	public void testDeleteNotExistingWebhook() throws Exception {
		baseEntityService.deleteAll(WebhookVO.class.getName());
		mockMvc.perform(delete("/api/entity/country/webhooks/{hookName}", "abc")).andDo(print()).andExpect(status().is5xxServerError()).andExpect(jsonPath("$.error").value("webhook with name 'abc' does not exist"));
	}

	@Test
	public void testCallWebhook() throws Exception {
		baseEntityService.deleteAll(WebhookVO.class.getName());

		stubFor(post(urlEqualTo("/countryhook")).willReturn(aResponse().withStatus(200)));

		mockMvc.perform(post("/api/entity/country/webhooks").content("{ \"name\": \"abc\", \"url\": \"http://localhost:8888/countryhook\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("abc"));

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AAA");
		baseEntityService.create(countryVO);

		Thread.sleep(500);

		verify(postRequestedFor(urlMatching("/countryhook")).withRequestBody(equalToJson("{ event: \"ON_CREATE\", payload: { countryIsoCode2: \"AA\" } }", JSONCompareMode.LENIENT)));
	}
}