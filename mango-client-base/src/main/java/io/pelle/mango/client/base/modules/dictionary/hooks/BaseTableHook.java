package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.vo.IBaseVO;

public abstract class BaseTableHook<VOType extends IBaseVO> implements IBaseTableHook<VOType> {

	@Override
	public String getStyleName(VOType tableRow) {
		return null;
	}

}
