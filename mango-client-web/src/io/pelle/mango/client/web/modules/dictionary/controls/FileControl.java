package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IFileControlModel;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class FileControl extends BaseDictionaryControl<IFileControlModel, Object>implements IFileControl {

	public FileControl(IFileControlModel fileControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(fileControlModel, parent);
	}

	public String getFileName() {
		if (getValue() != null) {
			FileVO fileVO = (FileVO) getValue();
			return fileVO.getFileName();
		} else {
			return MangoClientWeb.MESSAGES.fileNone();
		}
	}

	@Override
	public void parseValue(String valueString) {
		if (valueString == null) {
			setValue(null);
		} else {
			FileVO file = (FileVO) getValue();

			if (file == null) {
				file = new FileVO();
				setValue(file);
			}

			file.setFileUUID(valueString);
			setValue(file);
		}
	}

	@Override
	protected ParseResult parseValueInternal(String valueString) {
		return null;
	}

}
