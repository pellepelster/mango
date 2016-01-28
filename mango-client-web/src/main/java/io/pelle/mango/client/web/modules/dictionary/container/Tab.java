package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.ITab;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class Tab extends Composite implements ITab {

	public Tab(ICompositeModel compositeModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(compositeModel, parent);
	}

	@Override
	public String getName() {
		return getModel().getName();
	}

}
