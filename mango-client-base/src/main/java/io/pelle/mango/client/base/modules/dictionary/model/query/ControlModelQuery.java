package io.pelle.mango.client.base.modules.dictionary.model.query;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class ControlModelQuery extends BaseListQuery<IBaseControlModel>
{
	public ControlModelQuery(Collection<IBaseControlModel> list)
	{
		super(list);
	}

	public Optional<IBaseControlModel> getControlModelByAttributePath(final String attributePath)
	{
		return Iterables.tryFind(getList(), new Predicate<IBaseControlModel>()
		{
			@Override
			public boolean apply(@Nullable IBaseControlModel baseControlModel)
			{
				return attributePath.equals(baseControlModel.getAttributePath());
			}
		});
	}

}
