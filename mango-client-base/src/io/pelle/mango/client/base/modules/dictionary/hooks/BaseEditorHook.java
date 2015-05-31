package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.editor.IDictionaryEditor;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseEditorHook<VOType extends IBaseVO> implements IEditorHook<VOType> {

	@Override
	public void onSave(AsyncCallback<Boolean> asyncCallback, IDictionaryEditor<VOType> dictionaryEditor) {
		asyncCallback.onSuccess(true);
	}

	@Override
	public List<IButton> getEditorButtons(IDictionaryEditor<VOType> dictionaryEditor) {
		return Collections.emptyList();
	}

}
