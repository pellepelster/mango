package io.pelle.mango.client.base.modules.dictionary.editor;

import io.pelle.mango.client.base.modules.dictionary.model.editor.IEditorModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.Map;

public interface IDictionaryEditor<VOType extends IBaseVO> {

	VOType getVO();

	void addUpdateListener(IEditorUpdateListener updateListener);

	IEditorModel getModel();

	Map<String, Object> getContext();

}
