package io.pelle.mango.demo.model.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CountryVO;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public class EntityApiTest extends BaseDemoModelTest {

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
	public void testGetEntityApiIndex() throws Exception {
		mockMvc.perform(get("/entity/country/api/index")).andExpect(status().isOk());
	}

	@Test
	public void testGetEntityById() throws Exception {

		baseEntityService.deleteAll(CountryVO.class.getName());

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AA");

		countryVO = baseEntityService.create(countryVO);

		mockMvc.perform(get("/entity/country/api/rest/byid/{entityId}", countryVO.getId())).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.countryIsoCode2").value("AA"));
	}
	
	@Test
	public void testGetEntityByNaturalKey() throws Exception {

		baseEntityService.deleteAll(CountryVO.class.getName());

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AA");

		countryVO = baseEntityService.create(countryVO);

		mockMvc.perform(get("/entity/country/api/rest/bynaturalkey/{naturalKey}", "AA")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.countryIsoCode2").value("AA"));
	}
	
	@Test
	public void testGetEntitiesByQuery() throws Exception {

		baseEntityService.deleteAll(CountryVO.class.getName());

		CountryVO countryVO = new CountryVO();
		countryVO.setCountryIsoCode2("AA");
		countryVO.setCountryIsoCode3("AA");

		countryVO = baseEntityService.create(countryVO);

		mockMvc.perform(get("/entity/country/api/rest/query", "countryIsoCode2 == 'AA'")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.[0]countryIsoCode2").value("AA"));
	}

}