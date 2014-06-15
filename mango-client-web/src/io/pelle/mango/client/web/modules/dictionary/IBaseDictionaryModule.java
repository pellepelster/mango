package io.pelle.mango.client.web.modules.dictionary;

import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;

public interface IBaseDictionaryModule
{
	<ElementType> ElementType getElement(BaseModel<ElementType> baseModel);
}
