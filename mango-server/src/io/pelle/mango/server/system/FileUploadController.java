package io.pelle.mango.server.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.server.File;

@Controller
public class FileUploadController {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@RequestMapping(value = IFileControl.GWT_UPLOAD_REQUEST_MAPPING, method = RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "You can upload a file by posting to this same URL.";
	}

	public class FileUpload {

		private final String fileName;

		private final String fileUUID;

		public FileUpload(String fileName, String fileUUID) {
			super();
			this.fileName = fileName;
			this.fileUUID = fileUUID;
		}

		public String getFileName() {
			return fileName;
		}

		public String getFileUUID() {
			return fileUUID;
		}

	}

	public class FileUploadResponse {

		private final boolean success;

		private List<FileUpload> files = new ArrayList<>();

		public FileUploadResponse(boolean success) {
			super();
			this.success = success;
		}

		public boolean isSuccess() {
			return success;
		}

		public List<FileUpload> getFiles() {
			return files;
		};
	}

	// @RequestMapping(value = "/download", method = RequestMethod.GET)
	// public @ResponseBody void downloadFiles(HttpServletRequest request,
	// HttpServletResponse response) {
	//
	// ServletContext context = request.getServletContext();
	//
	// File downloadFile = new File("C:/JavaHonk/CustomJar.jar");
	// FileInputStream inputStream = null;
	// OutputStream outStream = null;
	//
	// try {
	// inputStream = new FileInputStream(downloadFile);
	//
	// // MIME type of the file
	// response.setContentType("application/octet-stream");
	// // Response header
	// response.setHeader("Content-Disposition", "attachment; filename=\"" +
	// file.getName() + "\"");
	//
	// // response header
	// String headerKey = "Content-Disposition";
	// String headerValue = String.format("attachment; filename=\"%s\"",
	// downloadFile.getName());
	// response.setHeader(headerKey, headerValue);
	//
	// // Write response
	// outStream = response.getOutputStream();
	// IOUtils.copy(inputStream, outStream);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// if (null != inputStream)
	// inputStream.close();
	// if (null != inputStream)
	// outStream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// }

	@RequestMapping(value = IFileControl.GWT_UPLOAD_REQUEST_MAPPING, method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResponse handleFileUpload(@RequestParam("files") List<MultipartFile> files) {

		if (!files.isEmpty()) {
			try {

				FileUploadResponse response = new FileUploadResponse(true);

				for (MultipartFile multiPartFile : files) {

					File file = new File();
					file.setFileName(multiPartFile.getOriginalFilename());
					file.setFileContent(multiPartFile.getBytes());
					file = baseEntityDAO.create(file);

					response.getFiles().add(new FileUpload(file.getFileName(), file.getFileUUID()));
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