package io.pelle.mango.client.base.modules.dictionary.editor;

import io.pelle.mango.client.base.vo.IBaseVO;

public interface IDictionaryEditor<VOType extends IBaseVO>
{
	VOType getVO();

	void addUpdateListener(IEditorUpdateListener updateListener);
}
