package io.pelle.mango.demo.server.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.MangoWebMvcApplicationContext;

@WebAppConfiguration
@Import({ MangoWebMvcApplicationContext.class })
public class ImportExportWebserviceTest extends BaseDemoTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testEntity1WSDL() throws Exception {
		mockMvc.perform(get("/schema/entity1_import_export.wsdl")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testEntity1XSD() throws Exception {
		mockMvc.perform(get("/schema/entity1.xsd")).andDo(print()).andExpect(status().isOk());
	}
}
