package io.pelle.mango.client.base.modules.dictionary.model.search;

import io.pelle.mango.client.base.modules.dictionary.hooks.BaseSearchHook;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.ArrayList;
import java.util.List;

public class SearchModel<VOType extends IBaseVO> extends BaseModel<Object> implements ISearchModel {
	private List<IFilterModel> filters = new ArrayList<IFilterModel>();

	private static final long serialVersionUID = 7452528927479882166L;

	private IResultModel resultModel;

	private String label;

	public void setLabel(String label) {
		this.label = label;
	}

	public SearchModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public List<IFilterModel> getFilterModels() {
		return this.filters;
	}

	@Override
	public IResultModel getResultModel() {
		return this.resultModel;
	}

	public void setResultModel(IResultModel resultModel) {
		this.resultModel = resultModel;
	}

	public void addSearchHook(BaseSearchHook<VOType> searchHook) {
		DictionaryHookRegistry.getInstance().addSearchHook(this, searchHook);
	}
}
