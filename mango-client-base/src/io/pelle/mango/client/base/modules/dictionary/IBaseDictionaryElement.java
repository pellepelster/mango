package io.pelle.mango.client.base.modules.dictionary;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseRootModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public interface IBaseDictionaryElement<ModelType extends IBaseModel> {
	IBaseDictionaryElement<?> getParent();

	ModelType getModel();

	IVOWrapper<? extends IBaseVO> getVOWrapper();

	IBaseRootElement<? extends IBaseRootModel> getRootElement();

	void update();

}
