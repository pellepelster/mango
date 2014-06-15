package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IBooleanControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

public class BooleanControlModel extends BaseControlModel<IBooleanControl> implements IBooleanControlModel
{

	private static final long serialVersionUID = 532719232119633708L;

	public BooleanControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

}
