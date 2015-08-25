package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IFileControlModel;

public interface IFileControl extends IBaseControl<Object, IFileControlModel> {

	static final String FILE_UPLOAD_BASE_URL = "gwtfilecontrol";

	static final String FILE_UPLOAD_URL = "upload";

	static final String FILE_GET_BASE_URL = "files";

	static final String FILE_GET_URL = "get";
}
