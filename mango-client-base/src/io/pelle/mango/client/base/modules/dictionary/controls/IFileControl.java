package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IFileControlModel;

public interface IFileControl extends IBaseControl<Object, IFileControlModel> {

	static final String FILE_REQUEST_BASE_URL = "gwtfilecontrol";

	static final String FILE_UPLOAD_URL = "upload";

	// static final String FILE_REQUEST_MAPPING_PUT = "put";
	//
	static final String FILE_REQUEST_MAPPING_GET = "get";
}
