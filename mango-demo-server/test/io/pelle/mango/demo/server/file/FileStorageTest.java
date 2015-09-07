package io.pelle.mango.demo.server.file;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;

import org.hamcrest.text.IsEmptyString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.server.MangoDemoTestApplicationContext;
import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.MangoWebMvcApplicationContext;

@WebAppConfiguration
@ContextConfiguration(classes = { MangoWebMvcApplicationContext.class, MangoDemoTestApplicationContext.class })
public class FileStorageTest extends BaseDemoTest {

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

	private String uploadFile(byte[] fileContent) throws Exception {

		MockMultipartFile multipartFile = new MockMultipartFile("files", "file1", null, new ByteArrayInputStream(fileContent));
		String content = mockMvc.perform(fileUpload("/files/put").file(multipartFile)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.files", hasSize(1)))
				.andExpect(jsonPath("$.files[0].fileName", is("file1"))).andExpect(jsonPath("$.files[0].fileUUID", not(IsEmptyString.isEmptyOrNullString()))).andReturn().getResponse().getContentAsString();

		JSONObject jsonObject = new JSONObject(content);
		JSONArray files = jsonObject.getJSONArray("files");
		String fileUUID = ((JSONObject) files.get(0)).getString("fileUUID");

		return fileUUID;
	}

	@Test
	public void testAddSingleUploadedFileToEntity() throws Exception {

		byte[] fileContent = new byte[] { 0xa, 0xb, 0xc };
		String fileUUID = uploadFile(fileContent);

		Entity1VO entity1 = new Entity1VO();

		FileVO file = new FileVO();
		file.setFileUUID(fileUUID);
		entity1.setFileEntityDatatype1(file);

		entity1 = baseEntityService.save(entity1);

		Assert.assertArrayEquals(fileContent, entity1.getFileEntityDatatype1().getFileContent());

	}

	@Test
	public void testAddMultipleUploadedFilesToEntity() throws Exception {

		byte[] fileContent1 = new byte[] { 0xa, 0xb, 0xc };
		byte[] fileContent2 = new byte[] { 0xd, 0xe, 0xf };

		String fileUUID1 = uploadFile(fileContent1);
		String fileUUID2 = uploadFile(fileContent2);

		Entity1VO entity1 = new Entity1VO();

		FileVO file1 = new FileVO();
		file1.setFileUUID(fileUUID1);
		entity1.getFileEntityDatatypes1().add(file1);

		FileVO file2 = new FileVO();
		file2.setFileUUID(fileUUID2);
		entity1.getFileEntityDatatypes1().add(file2);

		entity1 = baseEntityService.save(entity1);

		SelectQuery<Entity1VO> selectQuery = SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.ID.eq(entity1.getId())).join(Entity1VO.FILEENTITYDATATYPES1);

		entity1 = baseEntityService.filter(selectQuery).get(0);

		Assert.assertArrayEquals(fileContent1, entity1.getFileEntityDatatypes1().get(0).getFileContent());
		Assert.assertArrayEquals(fileContent2, entity1.getFileEntityDatatypes1().get(1).getFileContent());

	}

}