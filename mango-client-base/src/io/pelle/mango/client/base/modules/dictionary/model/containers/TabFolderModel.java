package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.container.ITabFolder;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.editor.EditorModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.FilterModel;

public abstract class TabFolderModel extends BaseContainerModel<ITabFolder>implements ITabfolderModel {

	private static final long serialVersionUID = 1832725605229414533L;

	public TabFolderModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	public TabFolderModel(String name, BaseContainerModel<?> parent) {
		super(name, parent);
		parent.getChildren().add(this);
	}

	public TabFolderModel(String name, EditorModel<?> parent) {
		super(name, parent);
		parent.setCompositeModel(this);
	}

	public TabFolderModel(String name, FilterModel parent) {
		super(name, parent);
		parent.setCompositeModel(this);
	}

}
