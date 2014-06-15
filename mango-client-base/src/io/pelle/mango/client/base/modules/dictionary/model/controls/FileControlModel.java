package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.modules.dictionary.hooks.IFileControlHook;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

public class FileControlModel extends BaseControlModel<IFileControl> implements IFileControlModel
{

	private static final long serialVersionUID = -4845737977264107889L;

	public FileControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	public void setFileControlHook(IFileControlHook fileControlHook)
	{
		DictionaryHookRegistry.getInstance().setFileControlHook(this, fileControlHook);
	}

}
