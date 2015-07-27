package io.pelle.mango.demo.server.entity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.client.showcase.CustomerVO;
import io.pelle.mango.demo.server.BaseDemoTest;

@WebAppConfiguration
public class EntityApiTest extends BaseDemoTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Before
	public void initTestData() {
		baseEntityService.deleteAll(CustomerVO.class.getName());
		baseEntityService.deleteAll(CountryVO.class.getName());
	}

	@Test
	public void testGetEntityApiIndex() throws Exception {
		mockMvc.perform(get("/api/entity/country/index")).andExpect(status().isOk());
	}

	@Test
	public void testGetEntityById() throws Exception {

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AA");

		countryVO = baseEntityService.create(countryVO);

		mockMvc.perform(get("/api/entity/country/byid/{entityId}", countryVO.getId())).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.countryIsoCode2").value("AA"));
	}

	@Test
	public void testGetEntityByNaturalKey() throws Exception {

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AA");

		countryVO = baseEntityService.create(countryVO);

		mockMvc.perform(get("/api/entity/country/bynaturalkey/{naturalKey}", "AA")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.countryIsoCode2").value("AA"));
	}

	@Test
	public void testGetEntitiesByQueryGet() throws Exception {

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AA");
		countryVO = baseEntityService.create(countryVO);

		countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("BB");
		countryVO.setCountryIsoCode3("BB");
		countryVO = baseEntityService.create(countryVO);

		mockMvc.perform(get("/api/entity/country/query").param("query", "countryIsoCode2 == 'AA'")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.[0]countryIsoCode2").value("AA"))
				.andExpect(jsonPath("$").value(Matchers.hasSize(1)));
	}

	@Test
	public void testGetEntitiesByQueryPost() throws Exception {

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AA");
		countryVO = baseEntityService.create(countryVO);

		countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("BB");
		countryVO.setCountryIsoCode3("BB");
		countryVO = baseEntityService.create(countryVO);

		mockMvc.perform(post("/api/entity/country/query").content("{ \"query\": \"countryIsoCode2 == 'AA'\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0]countryIsoCode2").value("AA")).andExpect(jsonPath("$").value(Matchers.hasSize(1)));
	}

}