package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.modules.dictionary.controls.IHierarchicalControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class HierarchicalControl extends BaseDictionaryControl<IHierarchicalControlModel, IHierarchicalVO> implements
		IHierarchicalControl
{

	public HierarchicalControl(IHierarchicalControlModel hierarchicalControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(hierarchicalControlModel, parent);
	}
	
	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		throw new RuntimeException("not implemented");
	}

}
