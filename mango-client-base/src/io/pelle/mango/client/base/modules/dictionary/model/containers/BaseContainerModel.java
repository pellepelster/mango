package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;

import java.util.ArrayList;
import java.util.List;

public class BaseContainerModel<ContainerElementType> extends BaseModel<ContainerElementType> implements IBaseContainerModel
{

	private static final long serialVersionUID = 4499895505899874913L;

	private List<IBaseContainerModel> container = new ArrayList<IBaseContainerModel>();

	private List<IBaseControlModel> controls = new ArrayList<IBaseControlModel>();

	public BaseContainerModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public List<IBaseContainerModel> getChildren()
	{
		return this.container;
	}

	@Override
	public List<IBaseControlModel> getControls()
	{
		return this.controls;
	}

}
