package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;

public interface ISearchHook<VOType extends IBaseVO>  {
	
	SelectQuery<VOType> beforeSearch(SelectQuery<VOType> selectQuery);
	
}
