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
import io.pelle.mango.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.controls.ReferenceControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

public class ReferenceDropdownControl<VOType extends IBaseVO> extends ListBox implements IGwtControl {

	public ReferenceDropdownControl(final ReferenceControl<VOType> referenceControl) {
		super(false);

		ensureDebugId(DictionaryModelUtil.getDebugId(referenceControl.getModel()));
		new ControlHelper(this, referenceControl, this, false);

		addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				referenceControl.setValue(null);
			}
		});

		ControlUtil.populateListBox(referenceControl.getModel(), this);
	}

	public static List<String> getSortedEnumList(IEnumerationControlModel<?> enumarationControlModel) {
		List<String> enumList = new ArrayList<String>();
		enumList.addAll(enumarationControlModel.getEnumerationMap().values());
		Collections.sort(enumList);

		return enumList;
	}

	public static String getEnumForText(IEnumerationControlModel<?> enumarationControlModel, String text) {
		if (text == null || text.isEmpty()) {
			return null;
		}

		for (Map.Entry<String, String> enumEntry : enumarationControlModel.getEnumerationMap().entrySet()) {
			if (enumEntry.getValue().equals(text)) {
				return enumEntry.getKey();
			}
		}

		throw new RuntimeException("no enum found for text '" + text + "'");
	}

	@Override
	public void setContent(Object content) {
		if (content != null) {
			if (content instanceof String || content instanceof Enum<?>) {

				for (int i = 0; i < getItemCount(); i++) {
					if (getItemText(i).equals(content.toString())) {
						setSelectedIndex(i);
					}
				}
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		} else {
			super.setSelectedIndex(0);
		}
	}

}
