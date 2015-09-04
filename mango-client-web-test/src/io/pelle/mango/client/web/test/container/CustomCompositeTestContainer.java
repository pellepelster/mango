package io.pelle.mango.client.web.test.container;

import io.pelle.mango.client.base.modules.dictionary.container.ICustomComposite;

public class CustomCompositeTestContainer extends BaseTestContainer {

	private ICustomComposite customComposite;

	public CustomCompositeTestContainer(ICustomComposite customComposite) {
		super();
		this.customComposite = customComposite;
	}

	public ICustomComposite getCustomComposite() {
		return customComposite;
	}
}
