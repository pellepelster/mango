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

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.controls.BaseCellControl.ViewData;

import java.util.Set;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;

public abstract class BaseCellControl<T> extends AbstractEditableCell<T, ViewData<T>> {
	static class ViewData<T> {
		private boolean isEditing;

		private boolean isEditingAgain;

		private IBaseTable.ITableRow<IBaseVO> tableRow;

		public ViewData(IBaseTable.ITableRow<IBaseVO> tableRow) {
			this.tableRow = tableRow;
		}

		public boolean isEditing() {
			return isEditing;
		}

		public boolean isEditingAgain() {
			return isEditingAgain;
		}

		public void setEditing(boolean isEditing) {
			boolean wasEditing = this.isEditing;
			this.isEditing = isEditing;

			if (!wasEditing && isEditing) {
				isEditingAgain = true;
			}
		}

		public IBaseTable.ITableRow<IBaseVO> getTableRow() {
			return tableRow;
		}

	}

	private final IBaseControlModel baseControlModel;

	public BaseCellControl(IBaseControlModel baseControlModel, Set<String> consumedEvents) {
		super(consumedEvents);

		this.baseControlModel = baseControlModel;
	}

	public BaseCellControl(IBaseControlModel baseControlModel, String... consumedEvents) {
		super(consumedEvents);

		this.baseControlModel = baseControlModel;
	}

	@Override
	public boolean isEditing(Context context, Element parent, T value) {
		return getOrInitViewData(context).isEditing();
	}

	protected InputElement getInputElement(Element parent) {
		return parent.getFirstChild().<InputElement> cast();
	}

	protected boolean enterPressed(NativeEvent event) {
		int keyCode = event.getKeyCode();
		return KeyUpEvent.getType().equals(event.getType()) && keyCode == KeyCodes.KEY_ENTER;
	}

	protected String format(Context context) {
		return getBaseControl(context).format();
	}

	protected IBaseControl<T, ?> getBaseControl(Context context) {
		return getOrInitViewData(context).getTableRow().getElement(baseControlModel);
	}

	protected ViewData<T> getOrInitViewData(Context context) {

		if (getViewData(context.getKey()) == null) {
			if (!(context.getKey() instanceof IBaseTable.ITableRow)) {
				throw new RuntimeException("IBaseTable.ITableRow expected as context key");
			}

			@SuppressWarnings("unchecked")
			IBaseTable.ITableRow<IBaseVO> tableRow = (IBaseTable.ITableRow<IBaseVO>) context.getKey();

			ViewData<T> viewData = new ViewData<T>(tableRow);
			setViewData(context.getKey(), viewData);
		}

		return getViewData(context.getKey());
	}

	protected void clearViewData(Context context) {
		setViewData(context.getKey(), null);
	}

}
