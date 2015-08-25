package io.pelle.mango.demo.server.test;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.entity.IBaseEntityService;

@SuppressWarnings("serial")
public class FileControlUploadServlet extends UploadAction {

	private IBaseEntityService baseEntityService;

	public FileControlUploadServlet() {
		super();
	}

	public IBaseEntityService getBaseEntityDAO() {

		if (baseEntityService == null) {

			ApplicationContext applicationContext = null;

			if (applicationContext == null) {
				applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			}

			baseEntityService = applicationContext.getBean(IBaseEntityService.class);
		}

		return baseEntityService;
	}

	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {

		String result = "";

		for (FileItem fileItem : getSessionFileItems(request)) {

			if (!fileItem.isFormField()) {

				String fileUUID = UUID.randomUUID().toString();

				FileVO file = new FileVO();
				file.setFileUUID(fileUUID);
				file.setFileContent(fileItem.get());
				file.setFileName(fileItem.getName());

				getBaseEntityDAO().create(file);

				result += fileUUID;
				;
			}
		}

		super.removeSessionFileItems(request);

		return result;
	}
}
