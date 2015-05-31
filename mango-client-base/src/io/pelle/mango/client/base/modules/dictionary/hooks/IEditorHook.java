package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.editor.IDictionaryEditor;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IEditorHook<VOType extends IBaseVO> {

	List<IButton> getEditorButtons(IDictionaryEditor<VOType> dictionaryEditor);

	void onSave(AsyncCallback<Boolean> asyncCallback, IDictionaryEditor<VOType> dictionaryEditor);

}
