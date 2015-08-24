package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

@SuppressWarnings("serial")
public abstract class ReferenceListModel extends BaseContainerModel<IReferenceListModel>implements IReferenceListModel {

	private String attributePath;

	public ReferenceListModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	public ReferenceListModel(String name, BaseContainerModel<?> parent) {
		super(name, parent);
		parent.getChildren().add(this);
	}

	@Override
	public String getAttributePath() {
		return this.attributePath;
	}

	public void setAttributePath(String attributePath) {
		this.attributePath = attributePath;
	}
}
