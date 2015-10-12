package io.pelle.mango.server.file;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.server.File;

@Controller
@RequestMapping(value = "files")
public class FileController {

	private static Logger LOG = Logger.getLogger(FileController.class);

	@Autowired
	private FileStorage fileStorage;

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@RequestMapping(value = "put", method = RequestMethod.GET)
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

	@RequestMapping(value = "/get/{fileUUID}", method = RequestMethod.GET)
	public @ResponseBody void downloadFiles(@PathVariable("fileUUID") String fileUUID, HttpServletRequest request, HttpServletResponse response) {

		File file = fileStorage.getFile(fileUUID);

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", file.getFileName());
		response.setHeader(headerKey, headerValue);

		try (OutputStream outStream = response.getOutputStream(); ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getFileContent())) {
			IOUtils.copy(inputStream, outStream);
		} catch (Exception e) {
			throw new FileNotFoundException(String.format("error retrieving file with uuid '%s'", fileUUID));
		}
	}

	@RequestMapping(value = "put", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public FileUploadResponse putSingle(@RequestParam("file") MultipartFile file) {
		return put(Arrays.asList(new MultipartFile[] { file }));
	}

	private FileUploadResponse put(@RequestParam("files") List<MultipartFile> files) {

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
				LOG.error(e);
				return new FileUploadResponse(false);
			}
		} else {
			return new FileUploadResponse(false);
		}
	}

}