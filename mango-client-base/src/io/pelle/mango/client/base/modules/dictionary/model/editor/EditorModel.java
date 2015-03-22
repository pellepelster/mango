package io.pelle.mango.client.base.modules.dictionary.model.editor;

import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.hooks.BaseEditorHook;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public class EditorModel<VOType extends IBaseVO> extends BaseModel<Object> implements IEditorModel {

	private static final long serialVersionUID = 7452528927479882166L;

	private boolean logDisplayEnabled;

	private String label;

	private ICompositeModel compositeModel;

	public EditorModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public ICompositeModel getCompositeModel() {
		return this.compositeModel;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setCompositeModel(ICompositeModel compositeModel) {
		this.compositeModel = compositeModel;
	}

	public void addEditorHook(BaseEditorHook<VOType> editorHook) {
		DictionaryHookRegistry.getInstance().addEditorHook(getName(), editorHook);
	}

	public void addEditorButton(IButton button) {
		DictionaryHookRegistry.getInstance().addEditorButton(getName(), button);
	}

	@Override
	public void enableLogDisplay() {
		logDisplayEnabled = true;
	}

	@Override
	public boolean isLogDisplayEnabled() {
		return logDisplayEnabled;
	}

}
