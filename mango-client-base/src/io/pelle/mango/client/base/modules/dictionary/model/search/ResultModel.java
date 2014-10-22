package io.pelle.mango.client.base.modules.dictionary.model.search;

import io.pelle.mango.client.base.modules.dictionary.model.BaseTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.ColumnLayout;
import io.pelle.mango.client.base.modules.dictionary.model.ColumnLayoutData;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;

import java.util.Collections;
import java.util.List;

public class ResultModel extends BaseTableModel<Object> implements IResultModel
{
	private static final long serialVersionUID = 7452528927479882166L;

	private int maxResults = DEFAULT_MAX_RESULTS;

	public ResultModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public List<IBaseContainerModel> getChildren()
	{
		return Collections.emptyList();
	}

	@Override
	public int getMaxResults()
	{
		return this.maxResults;
	}

	@Override
	public String getAttributePath()
	{
		return null;
	}
	
	@Override
	public ColumnLayoutData getLayoutData() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public ColumnLayout getLayout() {
		throw new RuntimeException("not implemented");
	}


}
