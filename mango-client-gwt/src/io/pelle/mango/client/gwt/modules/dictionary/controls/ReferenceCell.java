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

import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class ReferenceCell<VOType extends IBaseVO> extends AbstractCell<VOType> {
	private IReferenceControlModel referenceControlModel;

	public ReferenceCell(IReferenceControlModel referenceControlModel) {
		this.referenceControlModel = referenceControlModel;
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, IBaseVO value, SafeHtmlBuilder sb) {
		if (value != null) {
			sb.append(SafeHtmlUtils.fromString(DictionaryUtil.getLabel(referenceControlModel, value)));
		}
	}
}
