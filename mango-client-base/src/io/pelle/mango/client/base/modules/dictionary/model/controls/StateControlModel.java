package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IStateControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

@SuppressWarnings("serial")
public class StateControlModel extends BaseControlModel<IStateControl>implements IStateControlModel {

	public StateControlModel(String name, IBaseModel parent) {
		super(name, parent);
	}

}
