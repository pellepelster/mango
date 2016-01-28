package io.pelle.mango.client.web.test.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IBigDecimalControl;

import java.math.BigDecimal;

public class DecimalTestControl extends BaseTestControl<IBigDecimalControl, BigDecimal> {

	public DecimalTestControl(IBigDecimalControl control) {
		super(control);
		onUpdate();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

}
