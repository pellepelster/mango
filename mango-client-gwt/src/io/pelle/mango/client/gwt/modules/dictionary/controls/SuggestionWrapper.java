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

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.controls.SuggestCellControl.SuggestCellSuggestion;

public class SuggestionWrapper<VOTYPE extends IBaseVO> implements SuggestCellSuggestion<VOTYPE> {
	
	private final IReferenceControl.Suggestion<VOTYPE> suggestion;

	public SuggestionWrapper(IReferenceControl.Suggestion<VOTYPE> suggestion) {
		super();
		this.suggestion = suggestion;
	}

	@Override
	public String getDisplayString() {
		return suggestion.getLabel();
	}

	@Override
	public String getReplacementString() {
		return suggestion.getLabel();
	}

	@Override
	public VOTYPE getValue() {
		return suggestion.getVo();
	}

}