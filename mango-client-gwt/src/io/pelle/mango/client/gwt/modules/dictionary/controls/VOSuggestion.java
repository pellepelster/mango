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

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.controls.SuggestCellControl.SuggestCellSuggestion;

public class VOSuggestion<VOType extends IBaseVO> implements SuggestCellSuggestion<VOType> {

	private final String displayString;
	private final VOType vo;

	public VOSuggestion(String displayString, VOType vo) {
		super();
		this.displayString = displayString;
		this.vo = vo;
	}

	@Override
	public String getDisplayString() {
		return displayString;
	}

	@Override
	public String getReplacementString() {
		return displayString;
	}

	@Override
	public VOType getValue() {
		return vo;
	}

}