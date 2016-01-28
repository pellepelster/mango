package io.pelle.mango.client.web.util;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public interface ICustomCompositeFactory {
	
	Object create(String type, ICustomCompositeModel compositeModel, BaseDictionaryElement<? extends IBaseModel> parent);
	
}
