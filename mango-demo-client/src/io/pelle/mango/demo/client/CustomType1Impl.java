
package io.pelle.mango.demo.client;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.base.util.CustomComposite;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

@CustomComposite
public class CustomType1Impl extends io.pelle.mango.client.web.modules.dictionary.container.BaseCustomComposite {

	public CustomType1Impl(ICustomCompositeModel composite, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(composite, parent);
	}

}
