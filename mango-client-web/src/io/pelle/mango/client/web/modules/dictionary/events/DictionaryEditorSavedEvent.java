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
package io.pelle.mango.client.web.modules.dictionary.events;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;

import com.google.gwt.event.shared.GwtEvent;

public class DictionaryEditorSavedEvent extends GwtEvent<DictionaryEditorEventHandler>
{
	private final DictionaryEditor<? extends IBaseVO> editor;
	
	public DictionaryEditorSavedEvent(DictionaryEditor<? extends IBaseVO> editor)
	{
		super();
		this.editor = editor;
	}

	public DictionaryEditor<? extends IBaseVO> getEditor() {
		return editor;
	}

	public static Type<DictionaryEditorEventHandler> TYPE = new Type<DictionaryEditorEventHandler>();

	@Override
	public Type<DictionaryEditorEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(DictionaryEditorEventHandler handler)
	{
		handler.onSaved(editor);
	}

}