/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.web.modules.dictionary.editor;

import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.hooks.BaseEditorHook;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.modules.BaseDictionaryEditorModule;
import io.pelle.mango.client.web.modules.dictionary.DictionaryElementUtil;
import io.pelle.mango.client.web.modules.dictionary.IBaseDictionaryModule;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Dictionary editor module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModule<VOType extends IBaseVO> extends BaseDictionaryEditorModule implements IBaseDictionaryModule, IEditorUpdateListener {

	public final static String MODULE_LOCATOR = ModuleUtils.getBaseModuleUrl(MODULE_ID);

	public final static String EDITOR_UI_MODULE_ID = MODULE_ID;

	public final static String UI_MODULE_LOCATOR = ModuleUtils.getBaseUIModuleUrl(EDITOR_UI_MODULE_ID);

	public static final String getModuleUrlForDictionary(String dictionaryName, long voId) {
		return UI_MODULE_LOCATOR + "&" + EDITORDICTIONARYNAME_PARAMETER_ID + "=" + dictionaryName + "&" + ID_PARAMETER_ID + "=" + voId;
	}

	public static final String getModuleUrlForDictionary(String dictionaryName) {
		return UI_MODULE_LOCATOR + "&" + EDITORDICTIONARYNAME_PARAMETER_ID + "=" + dictionaryName;
	}

	public enum EditorMode {
		INSERT, UPDATE;
	}

	private DictionaryEditor<VOType> dictionaryEditor;

	private IDictionaryModel dictionaryModel;

	public DictionaryEditorModule(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);

		init(getEditorDictionaryName());
	}

	@Override
	public String getTitle() {
		return DictionaryUtil.getEditorLabel(this.dictionaryModel, this.dictionaryEditor);
	}

	public IDictionaryModel getDictionaryModel() {
		return this.dictionaryModel;
	}

	private void init(String dictionaryName) {

		DictionaryEditorModule.this.dictionaryModel = DictionaryModelProvider.getDictionary(dictionaryName);
		DictionaryEditorModule.this.dictionaryEditor = new DictionaryEditor<VOType>(this.dictionaryModel, getParameters());
		dictionaryEditor.addUpdateListener(this);

		AsyncCallback<Void> asyncCallback = new BaseErrorAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getModuleCallback().onSuccess(DictionaryEditorModule.this);
			}
		};

		if (hasId()) {
			DictionaryEditorModule.this.dictionaryEditor.load(getId(), asyncCallback);
		} else {
			DictionaryEditorModule.this.dictionaryEditor.newVO(asyncCallback);
		}

	}

	public DictionaryEditor<VOType> getDictionaryEditor() {
		return this.dictionaryEditor;
	}

	@Override
	public <ElementType> ElementType getElement(BaseModel<ElementType> baseModel) {
		return DictionaryElementUtil.getElement(this.dictionaryEditor, baseModel);
	}

	public void addUpdateListener(IEditorUpdateListener updateListener) {
		this.dictionaryEditor.addUpdateListener(updateListener);
	}

	public List<IButton> getEditorButtons() {

		List<IButton> buttons = new ArrayList<IButton>();

		if (DictionaryHookRegistry.getInstance().hasEditorHook(getDictionaryModel().getName())) {
			for (BaseEditorHook<VOType> baseEditorHook : DictionaryHookRegistry.getInstance().getEditorHook(getDictionaryModel().getName())) {
				buttons.addAll(baseEditorHook.getEditorButtons(this.dictionaryEditor));
			}
		}

		if (DictionaryHookRegistry.getInstance().hasEditorButtons(dictionaryEditor.getModel().getName())) {
			buttons.addAll(DictionaryHookRegistry.getInstance().getEditorButtons(dictionaryEditor.getModel().getName()));
		}

		return buttons;
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {

		boolean sameEditorDictionary = Objects.equal(getEditorDictionaryName(), ModuleUtils.getUrlParameter(moduleUrl, DictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID));

		boolean hasIdParameter = ModuleUtils.hasLongUrlParameter(moduleUrl, DictionaryEditorModule.ID_PARAMETER_ID);

		boolean sameId = false;

		if (hasIdParameter) {
			sameId = Objects.equal(getDictionaryEditor().getVO().getId(), ModuleUtils.getLongUrlParameter(moduleUrl, DictionaryEditorModule.ID_PARAMETER_ID));
		}

		return super.isInstanceOf(moduleUrl) && sameEditorDictionary && (hasIdParameter && sameId || !hasIdParameter);
	}

	@Override
	public String getModuleUrl() {
		return getModuleUrlForDictionary(getEditorDictionaryName(), dictionaryEditor.getVO().getOid());
	}

	public boolean isDirty() {
		return dictionaryEditor.isDirty();
	}

	@Override
	public void onUpdate() {
		fireUpdateListeners();
	}

	@Override
	public String getHelpText() {
		return getDictionaryModel().getEditorModel().getHelpText();
	}

	public boolean isLogDisplayEnabled() {
		return dictionaryEditor.getModel().isLogDisplayEnabled();
	}
}
