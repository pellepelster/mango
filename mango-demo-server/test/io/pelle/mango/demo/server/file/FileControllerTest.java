package io.pelle.mango.demo.server.file;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.hamcrest.text.IsEmptyString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.pelle.mango.demo.server.MangoDemoTestApplicationContext;
import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.MangoWebMvcApplicationContext;

@WebAppConfiguration
@ContextConfiguration(classes = { MangoWebMvcApplicationContext.class, MangoDemoTestApplicationContext.class })
public class FileControllerTest extends BaseDemoTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}

	@Test
	@Ignore
	public void testGetControlUploadServlet() throws Exception {

		HashMap<String, String> contentTypeParams = new HashMap<String, String>();
		contentTypeParams.put("boundary", "2625001916915724");
		MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

		mockMvc.perform(post("/gwtfilecontrol/put").contentType(mediaType)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Ignore
	public void testControlUploadServletNewSession() throws Exception {

		mockMvc.perform(get("/gwtfilecontrol?new_session=true&random=0.35148090892471373")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testUploadSingleFile() throws Exception {

		HashMap<String, String> contentTypeParams = new HashMap<String, String>();
		contentTypeParams.put("boundary", "265001916915724");
		MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

		MockMultipartFile multipartFile = new MockMultipartFile("file", "file1", null, new byte[] { 0xa, 0xb });

		mockMvc.perform(fileUpload("/files/put").file(multipartFile).contentType(mediaType)).andExpect(status().isOk()).andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.files", hasSize(1)))
				.andExpect(jsonPath("$.files[0].fileName", is("file1"))).andExpect(jsonPath("$.files[0].fileUUID", not(IsEmptyString.isEmptyOrNullString()))).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetFileInvalidUUID() throws Exception {
		mockMvc.perform(get("/files/get/{fileUUID}", "xxx")).andExpect(status().is4xxClientError());
	}

	@Test
	public void testGetFile() throws Exception {

		byte[] fileContent = new byte[] { 0xa, 0xb, 0xc };

		MockMultipartFile multipartFile = new MockMultipartFile("file", "file1", null, fileContent);
		String content = mockMvc.perform(fileUpload("/files/put").file(multipartFile)).andExpect(status().isOk()).andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.files", hasSize(1)))
				.andExpect(jsonPath("$.files[0].fileName", is("file1"))).andExpect(jsonPath("$.files[0].fileUUID", not(IsEmptyString.isEmptyOrNullString()))).andDo(MockMvcResultHandlers.print()).andReturn().getResponse()
				.getContentAsString();

		JSONObject jsonObject = new JSONObject(content);
		JSONArray files = jsonObject.getJSONArray("files");
		String fileUUD = ((JSONObject) files.get(0)).getString("fileUUID");

		byte[] downloadedFile = mockMvc.perform(get("/files/get/{fileUUID}", fileUUD)).andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();

		Assert.assertArrayEquals(fileContent, downloadedFile);

	}

	@Test
	public void testUploadNoFile() throws Exception {
		mockMvc.perform(fileUpload("/files/put")).andExpect(status().is4xxClientError()).andDo(MockMvcResultHandlers.print());
	}

}
