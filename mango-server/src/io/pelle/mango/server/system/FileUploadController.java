package io.pelle.mango.server.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;

@Controller
public class FileUploadController {

	@RequestMapping(value = IFileControl.GWT_UPLOAD_REQUEST_MAPPING, method = RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "You can upload a file by posting to this same URL.";
	}

	public class FileUploadResponse {

		private final boolean success;

		private List<FileVO> files = new ArrayList<>();

		public FileUploadResponse(boolean success) {
			super();
			this.success = success;
		}

		public boolean isSuccess() {
			return success;
		}

		public List<FileVO> getFiles() {
			return files;
		};
	}

	@RequestMapping(value = IFileControl.GWT_UPLOAD_REQUEST_MAPPING, method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResponse handleFileUpload(@RequestParam("files") List<MultipartFile> files) {

		if (!files.isEmpty()) {
			try {

				FileUploadResponse response = new FileUploadResponse(true);

				for (MultipartFile file : files) {

					FileVO fileVO = new FileVO();
					fileVO.setFileName(file.getOriginalFilename());
					fileVO.setFileContent(file.getBytes());

					response.getFiles().add(fileVO);
				}

				return response;

			} catch (Exception e) {
				return new FileUploadResponse(false);
			}
		} else {
			return new FileUploadResponse(false);
		}
	}

}