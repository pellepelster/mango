package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

@SuppressWarnings("serial")
public class CustomCompositeModel extends BaseContainerModel<ICustomCompositeModel>implements ICustomCompositeModel {

	private String type;

	private String entityattribute;

	public CustomCompositeModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setEntityattribute(String entityattribute) {
		this.entityattribute = entityattribute;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getAttributeName() {
		return entityattribute;
	}

}
