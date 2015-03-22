package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.modules.dictionary.controls.IButton;

public class TestButton {

	private IButton button;

	public TestButton(IButton button) {
		this.button = button;
	}

	public String getId() {
		return button.getId();
	}

}
