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

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable.TableUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import io.pelle.mango.client.web.modules.dictionary.result.DictionaryResult;

import com.google.gwt.user.client.ui.VerticalPanel;

public class DictionaryResultPanel<VOType extends IBaseVO> extends VerticalPanel {

	private final ResultCellTable<VOType> resultCellTable;

	public DictionaryResultPanel(final IDictionaryModel dictionaryModel, final DictionaryResult<VOType> dictionaryResult) {

		resultCellTable = new ResultCellTable<VOType>(dictionaryResult);
		resultCellTable.setWidth("100%");
		resultCellTable.addVOActivationHandler(new SimpleCallback<IBaseTable.ITableRow<VOType>>() {
			/** {@inheritDoc} */
			@Override
			public void onCallback(IBaseTable.ITableRow<VOType> tableRow) {
				DictionaryEditorModuleFactory.openEditorForId(dictionaryModel.getName(), tableRow.getVO().getId());
			}
		});

		dictionaryResult.addTableUpdateListeners(new TableUpdateListener() {
			@Override
			public void onUpdate() {
				resultCellTable.setRows(dictionaryResult.getRows());
			}
		});

		add(resultCellTable);
	}

}
