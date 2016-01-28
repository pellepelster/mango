package io.pelle.mango.client.base.modules.dictionary.model.query;

import java.util.Collection;

public class BaseListQuery<T>
{
	private Collection<T> list;

	public BaseListQuery(Collection<T> list)
	{
		super();
		this.list = list;
	}

	public Collection<T> getList()
	{
		return this.list;
	}

	public void setList(Collection<T> list)
	{
		this.list = list;
	}

}
