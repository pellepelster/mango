package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.container.IComposite;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.editor.EditorModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.FilterModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public abstract class FileListModel<VOType extends IBaseVO> extends BaseContainerModel<IComposite>implements ICompositeModel {

	private static final long serialVersionUID = 1832725605229414533L;
	
	private String attributePath;

	public FileListModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	public FileListModel(String name, BaseContainerModel<?> parent) {
		super(name, parent);
		parent.getChildren().add(this);
	}

	public FileListModel(String name, EditorModel<?> parent) {
		super(name, parent);
		parent.setCompositeModel(this);
	}

	public FileListModel(String name, FilterModel parent) {
		super(name, parent);
		parent.setCompositeModel(this);
	}

	public void setAttributePath(String attributePath) {
		this.attributePath = attributePath;
	}

	public String getAttributePath() {
		return attributePath;
	}
	
}
