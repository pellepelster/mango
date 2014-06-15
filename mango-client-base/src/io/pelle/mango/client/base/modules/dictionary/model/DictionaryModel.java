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
package io.pelle.mango.client.base.modules.dictionary.model;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.editor.IEditorModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.ISearchModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.ArrayList;
import java.util.List;

public class DictionaryModel extends BaseModel<Object> implements IDictionaryModel {

	private static final long serialVersionUID = 1732059266448744364L;

	private List<IBaseControlModel> labelControls = new ArrayList<IBaseControlModel>();

	private Class<? extends IBaseVO> voClass;

	private String pluralLabel;

	private String label;

	private ISearchModel searchModel;

	private IEditorModel editorModel;

	public DictionaryModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public IEditorModel getEditorModel() {
		return this.editorModel;
	}

	@Override
	public List<IBaseControlModel> getLabelControls() {
		return this.labelControls;
	}

	@Override
	public ISearchModel getSearchModel() {
		return this.searchModel;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public String getPluralLabel() {
		return this.pluralLabel;
	}

	@Override
	public Class<? extends IBaseVO> getVOClass() {
		return this.voClass;
	}

	public void setVoName(Class<? extends IBaseVO> voClass) {
		this.voClass = voClass;
	}

	public void setPluralLabel(String pluralLabel) {
		this.pluralLabel = pluralLabel;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setSearchModel(ISearchModel searchModel) {
		this.searchModel = searchModel;
	}

	public void setEditorModel(IEditorModel editorModel) {
		this.editorModel = editorModel;
	}

}
