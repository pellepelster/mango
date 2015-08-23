package io.pelle.mango.demo.server.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

public class MyServlet extends UploadAction {

	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {

		String ret = "";
		for (FileItem item : getSessionFileItems(request)) {
			if (!item.isFormField()) {
				// Do anything with the file.

				// Update the string to return;
				ret += "server message";
			}
		}
		super.removeSessionFileItems(request);

		return ret;
	}
}