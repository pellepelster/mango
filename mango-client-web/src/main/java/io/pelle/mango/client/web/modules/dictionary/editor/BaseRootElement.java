package io.pelle.mango.client.web.modules.dictionary.editor;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IBaseDictionaryElement;
import io.pelle.mango.client.base.modules.dictionary.IBaseRootElement;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseRootModel;
import io.pelle.mango.client.web.modules.dictionary.ValidationMessages;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.container.Composite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseRootElement<ModelType extends IBaseRootModel> extends BaseDictionaryElement<ModelType>implements IBaseRootElement<ModelType> {
	private final Composite rootComposite;

	private Map<IBaseDictionaryElement<?>, ValidationMessages> validationMessagesMap = new HashMap<IBaseDictionaryElement<?>, ValidationMessages>();

	private RootElementValidationMessages rootElementValidationMessages;

	public BaseRootElement(ModelType baseRootModel, BaseDictionaryElement<IBaseModel> parent) {
		super(baseRootModel, parent);

		this.rootComposite = new Composite(baseRootModel.getCompositeModel(), this);

		this.rootElementValidationMessages = new RootElementValidationMessages(this.validationMessagesMap);
	}

	public Composite getRootComposite() {
		return this.rootComposite;
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren() {
		return this.rootComposite.getAllChildren();
	}

	@Override
	public ValidationMessages getValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement) {
		if (!this.validationMessagesMap.containsKey(baseDictionaryElement)) {
			this.validationMessagesMap.put(baseDictionaryElement, new ValidationMessages());
		}

		return this.validationMessagesMap.get(baseDictionaryElement);
	}

	@Override
	public void addValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement, List<IValidationMessage> elementValidationMessages) {
		getValidationMessages(baseDictionaryElement).addAll(elementValidationMessages);
	}

	@Override
	public void clearValidationMessages(IBaseDictionaryElement<?> baseDictionaryElement) {
		getValidationMessages(baseDictionaryElement).clear();
		this.validationMessagesMap.remove(baseDictionaryElement);
	}

	public IValidationMessages getValidationMessages() {
		return this.rootElementValidationMessages;
	}

}
