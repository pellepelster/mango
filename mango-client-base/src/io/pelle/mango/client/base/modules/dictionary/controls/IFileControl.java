package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IFileControlModel;

// TODO find a way to use FIleVO
public interface IFileControl extends IBaseControl<Object, IFileControlModel>
{
	static final String FILE_DOWNLOAD_REQUEST_MAPPING = "filedownload";

	static final String GWT_UPLOAD_REQUEST_MAPPING = "gwtcontrolupload";

	static final String REQUEST_MAPPING_GET_FILE = "getFile";
}
