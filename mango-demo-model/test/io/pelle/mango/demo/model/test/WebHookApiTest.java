package io.pelle.mango.demo.model.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.pelle.mango.client.api.WebHookVO;
import io.pelle.mango.client.entity.IBaseEntityService;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public class WebHookApiTest extends BaseDemoModelTest {

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
}