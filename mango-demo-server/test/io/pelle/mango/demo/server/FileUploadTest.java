package io.pelle.mango.demo.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public class FileUploadTest extends BaseDemoTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	public void testUpload() throws Exception {

	            String endpoint = "/gwtcontrolupload";

	            File tempFile = Files.createTempFile("gwtcontrolupload", "tmp").toFile();
	            PrintWriter pw = new PrintWriter(tempFile);
	            pw.write("xxx");
	            pw.close();
	            
	            FileInputStream fis = new FileInputStream(tempFile);
	            MockMultipartFile multipartFile = new MockMultipartFile("file", fis);

	            HashMap<String, String> contentTypeParams = new HashMap<String, String>();
	            contentTypeParams.put("boundary", "265001916915724");
	            MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

	            String result = mockMvc.perform(
	                    post(endpoint).content(multipartFile.getBytes())
	                    .contentType(mediaType)).andReturn().getResponse().getContentAsString();
	            
	            System.out.println(result);
	            //        .andExpect(status().isOk());
	}
}
