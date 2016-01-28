package io.pelle.mango.server.test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;

import org.hamcrest.text.IsEmptyString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class FileTestUtils {

	public static String uploadFile(MockMvc mockMvc, String fileName, byte[] content) {

		try {
			MockMultipartFile multipartFile = new MockMultipartFile(fileName, new ByteArrayInputStream(content));

			String result = mockMvc.perform(fileUpload("/files/putsingle").file(multipartFile)).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.files", hasSize(1)))
					.andExpect(jsonPath("$.files[0].fileName", is(fileName))).andExpect(jsonPath("$.files[0].fileUUID", not(IsEmptyString.isEmptyOrNullString()))).andDo(MockMvcResultHandlers.print()).andReturn().getResponse()
					.getContentAsString();

			JSONObject jsonObject = new JSONObject(result);
			JSONArray files = jsonObject.getJSONArray("files");
			String fileUUID = ((JSONObject) files.get(0)).getString("fileUUID");

			return fileUUID;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}