package io.pelle.mango.client.gwt.modules.dictionary.controls;

import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

import com.google.gwt.user.client.ui.Label;

public class ReadonlyControl extends Label {

	private BaseDictionaryControl<?, ?> baseControl;

	public ReadonlyControl(BaseDictionaryControl<?, ?> baseControl) {
		super();
		this.baseControl = baseControl;
	}

	public void setContent(Object content) {
		setText(baseControl.format());
	}

}
