package io.pelle.mango.client.web.modules.dictionary.base;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IBaseDictionaryElement;
import io.pelle.mango.client.base.modules.dictionary.IBaseRootElement;
import io.pelle.mango.client.base.modules.dictionary.IVOWrapper;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.List;

public abstract class BaseDictionaryElement<ModelType extends IBaseModel> implements IBaseDictionaryElement<ModelType> {

	private ModelType model;

	private BaseDictionaryElement<? extends IBaseModel> parent;

	public BaseDictionaryElement(ModelType model, BaseDictionaryElement<? extends IBaseModel> parent) {
		super();
		this.model = model;
		this.parent = parent;
	}

	@Override
	public ModelType getModel() {
		return this.model;
	}

	@Override
	public IVOWrapper<? extends IBaseVO> getVOWrapper() {
		return getParent().getVOWrapper();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.model == null) ? 0 : this.model.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseDictionaryElement<?> other = (BaseDictionaryElement<?>) obj;
		if (this.model == null) {
			if (other.model != null)
				return false;
		} else if (!this.model.equals(other.model))
			return false;
		return true;
	}

	@Override
	public IBaseDictionaryElement<? extends IBaseModel> getParent() {
		return this.parent;
	}

	@Override
	public IBaseRootElement<?> getRootElement() {
		return getParent().getRootElement();
	}

	public IValidationMessages getValidationMessages() {
		return getRootElement().getValidationMessages(this);
	}

	public void addValidationMessage(IValidationMessage validationMessage) {
		getRootElement().getValidationMessages(this).addValidationMessage(validationMessage);
		update();
	}

	protected void update() {
	}

	public abstract List<? extends BaseDictionaryElement<?>> getAllChildren();
}
