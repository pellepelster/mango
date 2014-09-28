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
package io.pelle.mango.client.gwt.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.controls.ReferenceControl;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class GwtReferenceTextControl<VOType extends IBaseVO> extends SuggestBox implements IGwtControl {

	private VOType vo;

	private final ReferenceControl<VOType> referenceControl;

	public GwtReferenceTextControl(final ReferenceControl<VOType> referenceControl) {
		super(new VOSuggestOracle<VOType>(referenceControl.getModel()));

		ensureDebugId(DictionaryModelUtil.getDebugId(referenceControl.getModel()));
		setLimit(5);
		this.referenceControl = referenceControl;
		new ControlHelper(this, referenceControl, this, false);

		addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> selectionEvent) {
				if (selectionEvent.getSelectedItem() instanceof VOSuggestion) {
					VOSuggestion<VOType> voSuggestion = (VOSuggestion<VOType>) selectionEvent.getSelectedItem();
					vo = voSuggestion.getValue();
					referenceControl.setValue(vo);
				}

			}
		});

	}

	@Override
	public void setContent(Object content) {
		if (content != null) {
			if (content instanceof IBaseVO) {
				vo = (VOType) content;
				setText(DictionaryUtil.getLabel(referenceControl.getModel(), vo));
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		} else {
			vo = null;
			setText("");
		}
	}

}
