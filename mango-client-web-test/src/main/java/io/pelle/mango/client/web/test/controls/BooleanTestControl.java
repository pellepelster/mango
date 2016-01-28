package io.pelle.mango.client.web.test.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IBooleanControl;

public class BooleanTestControl extends BaseTestControl<IBooleanControl, Boolean> {

	public BooleanTestControl(IBooleanControl control) {
		super(control);
		onUpdate();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

	}

	public void check() {
		setValue(true);
	}

	public void uncheck() {
		setValue(false);
	}

}
