package io.pelle.mango.server.system;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.server.File;

@Controller
@RequestMapping(value = "file")
public class FileController {

	private static Logger LOG = Logger.getLogger(FileController.class);

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

	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class FileNotFoundException extends RuntimeException {
	}

	@RequestMapping(value = "/get/{fileUUID}", method = RequestMethod.GET)
	public @ResponseBody void downloadFiles(@PathVariable("fileUUID") String fileUUID, HttpServletRequest request, HttpServletResponse response) {

		SelectQuery<File> query = SelectQuery.selectFrom(File.class).where(File.FILEUUID.eq(fileUUID));

		List<File> files = baseEntityDAO.filter(query);

		if (files.isEmpty()) {
			LOG.error(String.format("file with uuid '%s' not found", fileUUID));
			throw new FileNotFoundException();
		} else {

			File file = files.get(0);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", file.getFileName());
			response.setHeader(headerKey, headerValue);

			try (OutputStream outStream = response.getOutputStream(); ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getFileContent())) {
				IOUtils.copy(inputStream, outStream);
			} catch (Exception e) {
				LOG.error(String.format("error retrieving file with uuid '%s'", fileUUID));
				throw new FileNotFoundException();
			}
		}
	}

	@RequestMapping(value = "put", method = RequestMethod.POST)
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