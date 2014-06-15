package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseTableElement<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends BaseContainerElement<ModelType> implements IBaseTable<VOType> {

	private TableRowList<VOType, ModelType> rowList = new TableRowList<VOType, ModelType>(new ArrayList<VOType>(), this);;

	private List<ITableRow<VOType>> selection = new ArrayList<ITableRow<VOType>>();

	private List<TableUpdateListener> tableUpdateListeners = new ArrayList<TableUpdateListener>();

	public BaseTableElement(ModelType baseTable, BaseDictionaryElement<IBaseModel> parent) {
		super(baseTable, parent);
	}

	private void fireTableUpdateListeners() {
		for (TableUpdateListener tableUpdateListener : this.tableUpdateListeners) {
			tableUpdateListener.onUpdate();
		}
	}

	@Override
	public void addTableUpdateListeners(TableUpdateListener tableUpdateListener) {
		this.tableUpdateListeners.add(tableUpdateListener);
	}

	@Override
	public List<ITableRow<VOType>> getRows() {
		return this.rowList;
	}

	public void setRows(List<VOType> vos) {
		this.rowList = new TableRowList<VOType, ModelType>(vos, this);

		fireTableUpdateListeners();
	}

	private void setSelection(ITableRow<VOType> tableRow) {
		this.selection.clear();
		this.selection.add(tableRow);
	}

	private void setDefaultSelection() {
		this.selection.clear();
		if (!this.selection.isEmpty()) {
			this.selection.add(this.rowList.get(0));
		}
	}

	protected ITableRow<VOType> addRow(VOType vo) {
		TableRow<VOType, ModelType> tableRow = new TableRow<VOType, ModelType>(vo, this);
		this.rowList.add(tableRow);
		setSelection(tableRow);

		fireTableUpdateListeners();

		return tableRow;
	}

	public ITableRow<VOType> getTableRow(int rowIndex) {
		return this.rowList.get(rowIndex);
	}

	@Override
	public List<ITableRow<VOType>> getSelection() {
		return this.selection;
	}

	@Override
	public void delete(List<IBaseTable.ITableRow<VOType>> rowsToDelete, final AsyncCallback<List<ITableRow<VOType>>> asyncCallback) {
		this.rowList.removeAll(rowsToDelete);

		setDefaultSelection();

		fireTableUpdateListeners();

		asyncCallback.onSuccess(this.rowList);
	}

	@Override
	public void delete(AsyncCallback<List<IBaseTable.ITableRow<VOType>>> asyncCallback) {
		delete(getSelection(), asyncCallback);
	}

	@Override
	protected void update() {
		if (getModel().getAttributePath() != null) {
			List<VOType> vos = (List<VOType>) getVOWrapper().get(getModel().getAttributePath());

			this.rowList = new TableRowList<VOType, ModelType>(vos, this);

			fireTableUpdateListeners();
		}
	}

	@Override
	public void delete(ITableRow<VOType> tableRowToDelete, AsyncCallback<List<IBaseTable.ITableRow<VOType>>> asyncCallback) {
		List<ITableRow<VOType>> rowsToDelete = new ArrayList<ITableRow<VOType>>();
		rowsToDelete.add(tableRowToDelete);
		delete(rowsToDelete, asyncCallback);
	}
}
