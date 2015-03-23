package io.pelle.mango.client.base.modules.dictionary;

import io.pelle.mango.client.base.modules.dictionary.editor.IDictionaryEditor;

public class DictionaryContext {

	private IDictionaryEditor<?> dictionaryEditor;

	public DictionaryContext(IDictionaryEditor<?> dictionaryEditor) {
		this.dictionaryEditor = dictionaryEditor;
	}

	public boolean hasEditor() {
		return dictionaryEditor != null;
	}

	public IDictionaryEditor<?> getDictionaryEditor() {
		return dictionaryEditor;
	}

}
