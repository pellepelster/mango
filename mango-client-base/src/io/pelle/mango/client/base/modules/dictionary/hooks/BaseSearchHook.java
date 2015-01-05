package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;

public abstract class BaseSearchHook<VOType extends IBaseVO> implements ISearchHook<VOType> {

	@Override
	public SelectQuery<VOType> beforeSearch(SelectQuery<VOType> selectQuery) {
		return selectQuery;
	}

}
