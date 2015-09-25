package io.pelle.mango.client.web.test.container;

import org.junit.Assert;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseContainer;

public class BaseTestContainer implements IUpdateListener {
	
	private boolean enabled = true;

	private IBaseContainer<? extends IUpdateListener> baseContainer;

	public BaseTestContainer() {
		super();
	}

	public BaseTestContainer(IBaseContainer<IUpdateListener> baseContainer) {
		super();
		
		this.baseContainer = baseContainer;
		baseContainer.addUpdateListener(this);
	}


	public void assertIsDisabled() {
		Assert.assertFalse(enabled);
	}
	
	public void assertIsEnabled() {
		Assert.assertTrue(enabled);
	}

	@Override
	public void onUpdate() {
		enabled = baseContainer.isEnabled();
	}

}
