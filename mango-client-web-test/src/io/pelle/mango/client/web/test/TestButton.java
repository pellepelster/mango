package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.modules.dictionary.DictionaryContext;
import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.editor.IDictionaryEditor;

public class TestButton {

	private IButton button;

	private IDictionaryEditor<?> dictionaryEditor;

	public TestButton(IDictionaryEditor<?> dictionaryEditor, IButton button) {
		this.button = button;
		this.dictionaryEditor = dictionaryEditor;
	}

	public String getId() {
		return button.getId();
	}

	public void push() {
		button.onClick(null, new DictionaryContext(dictionaryEditor));
	}

}
