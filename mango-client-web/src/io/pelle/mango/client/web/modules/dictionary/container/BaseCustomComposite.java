package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.ICustomComposite;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public abstract class BaseCustomComposite extends BaseContainerElement<ICustomCompositeModel, IUpdateListener> implements ICustomComposite {

	public BaseCustomComposite(ICustomCompositeModel composite, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(composite, parent);
	}

}
