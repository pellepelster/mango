package io.pelle.mango.client.base.modules.dictionary;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseRootModel;

import java.util.List;

public interface IBaseRootElement<ModelType extends IBaseRootModel> extends IBaseDictionaryElement<ModelType> {

	IValidationMessages getValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement);

	void addValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement, List<IValidationMessage> elementValidationMessages);

	void clearValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement);
}
