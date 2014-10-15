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
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.controls.ReferenceControl;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class GwtReferenceTextControl<VOTYPE extends IBaseVO> extends SuggestBox implements IGwtControl {

	private ReferenceControl<VOTYPE> referenceControl;

	public GwtReferenceTextControl(final ReferenceControl<VOTYPE> referenceControl) {
		super(new MangoSuggestOracle<VOTYPE>(referenceControl));
		this.referenceControl = referenceControl;

		ensureDebugId(DictionaryModelUtil.getDebugId(referenceControl.getModel()));
		setLimit(5);
		new ControlHelper(this, referenceControl, this, false);

		addSelectionHandler(new SelectionHandler<Suggestion>() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSelection(SelectionEvent<Suggestion> selectionEvent) {
				if (selectionEvent.getSelectedItem() instanceof SuggestionWrapper) {
					SuggestionWrapper<VOTYPE> suggestionWrapper = (SuggestionWrapper<VOTYPE>) selectionEvent.getSelectedItem();
					referenceControl.setValue(suggestionWrapper.getValue());
				}

			}
		});

	}

	@Override
	public void setContent(Object content) {
		setText(referenceControl.format());
	}

}
