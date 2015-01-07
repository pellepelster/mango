package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.vo.IBaseVO;

public interface IBaseTableHook<VOType extends IBaseVO> {

	String getStyleName(VOType tableRow);

}
