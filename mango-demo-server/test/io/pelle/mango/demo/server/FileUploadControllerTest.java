package io.pelle.mango.demo.server;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public class FileUploadControllerTest extends BaseDemoTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testUploadSingleFile() throws Exception {

		File tempFile = Files.createTempFile("gwtcontrolupload", "tmp").toFile();
		PrintWriter pw = new PrintWriter(tempFile);
		pw.write("xxx");
		pw.close();

		MockMultipartFile multipartFile = new MockMultipartFile("files", "file1", null, new FileInputStream(tempFile));
		mockMvc.perform(fileUpload("/gwtcontrolupload").file(multipartFile)).andExpect(status().isOk()).andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.files", hasSize(1))).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testUploadNoFile() throws Exception {
		mockMvc.perform(fileUpload("/gwtcontrolupload")).andExpect(status().isOk()).andExpect(jsonPath("$.success", is(false))).andDo(MockMvcResultHandlers.print());
	}

}
