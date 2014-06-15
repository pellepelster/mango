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

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

public class ControlUtil {

	public static void populateListBox(final IReferenceControlModel referenceControlModel, final ListBox listBox) {
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(referenceControlModel.getDictionaryName());

		SelectQuery<IBaseVO> selectQuery = (SelectQuery<IBaseVO>) SelectQuery.selectFrom(dictionaryModel.getVOClass());

		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(selectQuery, new AsyncCallback<List<IBaseVO>>() {

			@Override
			public void onFailure(Throwable caught) {
				throw new RuntimeException("error loading reference  content for '" + referenceControlModel.getName() + "'");
			}

			@Override
			public void onSuccess(List<IBaseVO> result) {
				listBox.addItem("");

				for (IBaseVO vo : result) {
					listBox.addItem(DictionaryUtil.getLabel(referenceControlModel, vo));
				}
			}
		});
	}
}
