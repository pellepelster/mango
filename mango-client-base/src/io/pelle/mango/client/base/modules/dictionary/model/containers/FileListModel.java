package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.container.IFileList;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public abstract class FileListModel<VOType extends IBaseVO> extends BaseContainerModel<IFileList> implements IFileListModel {

	private static final long serialVersionUID = 1832725605229414533L;
	
	private String attributePath;

	public FileListModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	public FileListModel(String name, BaseContainerModel<?> parent) {
		super(name, parent);
		parent.getChildren().add(this);
	}

	public void setAttributePath(String attributePath) {
		this.attributePath = attributePath;
	}

	@Override
	public String getAttributePath() {
		return attributePath;
	}
	
}
