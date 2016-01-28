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
import io.pelle.mango.client.gwt.modules.dictionary.controls.table.SuggestCellControl.SuggestCellSuggestion;
import io.pelle.mango.client.gwt.utils.HtmlUtils;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class SuggestionWrapper<VOTYPE extends IBaseVO> implements SuggestCellSuggestion<VOTYPE> {

	private final IReferenceControl.Suggestion<VOTYPE> suggestion;

	private final String text;

	public SuggestionWrapper(IReferenceControl.Suggestion<VOTYPE> suggestion, String text) {
		super();
		this.suggestion = suggestion;
		this.text = text;
	}

	@Override
	public String getDisplayString() {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		HtmlUtils.highlightTexts(text, suggestion.getLabel(), sb);
		return sb.toSafeHtml().asString();
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