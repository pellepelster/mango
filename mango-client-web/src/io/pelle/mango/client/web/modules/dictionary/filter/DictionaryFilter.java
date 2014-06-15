package io.pelle.mango.client.web.modules.dictionary.filter;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.IFilterModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.container.Composite;
import io.pelle.mango.client.web.modules.dictionary.editor.BaseRootElement;

import java.util.List;

public class DictionaryFilter<VOType extends IBaseVO> extends BaseRootElement<IFilterModel>
{
	private final Composite rootComposite;

	public DictionaryFilter(IFilterModel filterModel, BaseDictionaryElement<IBaseModel> parent)
	{
		super(filterModel, parent);

		this.rootComposite = new Composite(filterModel.getCompositeModel(), this);
	}

	@Override
	public Composite getRootComposite()
	{
		return this.rootComposite;
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren()
	{
		return this.rootComposite.getAllChildren();
	}

	@Override
	public BaseRootElement<?> getRootElement()
	{
		return this;
	}

}
