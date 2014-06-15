package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IHierarchicalControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

public class HierarchicalControlModel extends BaseControlModel<IHierarchicalControl> implements IHierarchicalControlModel
{

	private static final long serialVersionUID = -947831635255212542L;

	private String hierarchicalId;

	public HierarchicalControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getHierarchicalId()
	{
		return this.hierarchicalId;
	}

	public void setHierarchicalId(String hierarchicalId)
	{
		this.hierarchicalId = hierarchicalId;
	}

}
