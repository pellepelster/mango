package io.pelle.mango.client.web.test.controls;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;

import org.hamcrest.text.IsEmptyString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;

public class FileTestControl extends BaseTestControl<IFileControl, Object> {

	private String fileName;

	private String fileUUID;

	public FileTestControl(IFileControl control) {
		super(control);
	}

	public void uploadData(byte[] content, MockMvc mockMvc) {

		try {
			MockMultipartFile multipartFile = new MockMultipartFile("files", "file1", null, new ByteArrayInputStream(content));
			String result = mockMvc.perform(fileUpload("/files/put").file(multipartFile)).andExpect(status().isOk()).andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.files", hasSize(1)))
					.andExpect(jsonPath("$.files[0].fileName", is("file1"))).andExpect(jsonPath("$.files[0].fileUUID", not(IsEmptyString.isEmptyOrNullString()))).andDo(MockMvcResultHandlers.print()).andReturn().getResponse()
					.getContentAsString();

			JSONObject jsonObject = new JSONObject(result);
			JSONArray files = jsonObject.getJSONArray("files");
			String fileUUID = ((JSONObject) files.get(0)).getString("fileUUID");
			getControl().setFileNameUUID(null, fileUUID);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		this.fileName = getControl().getFileName();
		this.fileUUID = getControl().getFileUUID();
	}

	public void assertContent(byte[] expectedContent, MockMvc mockMvc) {

		FileVO file = (FileVO) getValue();

		try {
			byte[] content = mockMvc.perform(get("/files/get/{fileUUID}", file.getFileUUID())).andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();
			Assert.assertArrayEquals(expectedContent, content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void delete() {
		getControl().delete();
	}

	public void assertFileName(String expectedFileName) {
		Assert.assertEquals(expectedFileName, fileName);

	}

	public void assertIsEmpty() {
		Assert.assertTrue(fileUUID == null);

	}

}
