package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.container.IStateContainer;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public abstract class StateModel<VOType extends IBaseVO> extends BaseContainerModel<IStateContainer> implements IStateModel {

	private static final long serialVersionUID = 1832725605229414533L;
	
	public StateModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	public StateModel(String name, BaseContainerModel<?> parent) {
		super(name, parent);
		parent.getChildren().add(this);
	}


	
}
