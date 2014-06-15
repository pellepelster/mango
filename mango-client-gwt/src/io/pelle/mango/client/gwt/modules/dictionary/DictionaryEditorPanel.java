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
package io.pelle.mango.client.gwt.modules.dictionary;

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ColumnLayoutStrategy;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;

import com.google.gwt.user.client.ui.VerticalPanel;

public class DictionaryEditorPanel<VOType extends IBaseVO> extends VerticalPanel
{

	private final ColumnLayoutStrategy layoutStrategy = new ColumnLayoutStrategy(LAYOUT_TYPE.EDITOR);

	/**
	 * Constructor for {@link DictionaryEditorPanel}
	 * 
	 * @param dictionaryEditorModule
	 */
	public DictionaryEditorPanel(DictionaryEditorModule<VOType> dictionaryEditorModule)
	{
		setWidth("100%");
		layoutStrategy.createLayout(this, dictionaryEditorModule.getDictionaryEditor().getRootComposite());
	}

}
