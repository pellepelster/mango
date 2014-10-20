package io.pelle.mango.client.web.test.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IDateControl;

import java.util.Date;

public class DateTestControl extends BaseTestControl<IDateControl, Date> {

	public DateTestControl(IDateControl control) {
		super(control);
		onUpdate();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

	}

}
