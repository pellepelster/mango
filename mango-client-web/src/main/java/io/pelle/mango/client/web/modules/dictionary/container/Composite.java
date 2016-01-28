package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class Composite extends BaseContainerElement<ICompositeModel, IUpdateListener> {

	public Composite(ICompositeModel composite, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(composite, parent);
	}

}
