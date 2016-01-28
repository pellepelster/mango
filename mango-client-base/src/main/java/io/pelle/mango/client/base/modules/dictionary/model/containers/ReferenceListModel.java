package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.container.IReferenceList;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;

@SuppressWarnings("serial")
public abstract class ReferenceListModel<VOTYPE extends IBaseVO> extends BaseContainerModel<IReferenceList<VOTYPE>>implements IReferenceListModel<VOTYPE> {

	private String attributePath;

	private String dictionaryName;

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

	@Override
	public String getDictionaryName() {
		return this.dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}
}
