package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseTableElement<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends BaseContainerElement<ModelType, IUpdateListener> implements IBaseTable<VOType> {

	private TableRowList<VOType, ModelType> rowList = new TableRowList<VOType, ModelType>(new ArrayList<VOType>(), this);;

	private List<ITableRow<VOType>> selection = new ArrayList<ITableRow<VOType>>();

	private Optional<SimpleCallback<VOType>> activationCallback = Optional.absent();

	public BaseTableElement(ModelType baseTable, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(baseTable, parent);
	}

	@Override
	public List<ITableRow<VOType>> getRows() {
		return this.rowList;
	}

	public void setRows(List<VOType> vos) {
		this.rowList = new TableRowList<VOType, ModelType>(vos, this);

		fireUpdateListeners();
	}

	public void setSelection(ITableRow<VOType> tableRow) {
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

		fireUpdateListeners();

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

		fireUpdateListeners();

		asyncCallback.onSuccess(this.rowList);
	}

	@Override
	public void delete(AsyncCallback<List<IBaseTable.ITableRow<VOType>>> asyncCallback) {
		delete(getSelection(), asyncCallback);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update() {

		if (getModel().getAttributePath() != null) {

			List<VOType> vos = (List<VOType>) getVOWrapper().get(getModel().getAttributePath());

			this.rowList = new TableRowList<VOType, ModelType>(vos, this);

			fireUpdateListeners();
		}
	}

	@Override
	public void delete(ITableRow<VOType> tableRowToDelete, AsyncCallback<List<IBaseTable.ITableRow<VOType>>> asyncCallback) {
		List<ITableRow<VOType>> rowsToDelete = new ArrayList<ITableRow<VOType>>();
		rowsToDelete.add(tableRowToDelete);
		delete(rowsToDelete, asyncCallback);
	}

	public void addActivateCallback(SimpleCallback<VOType> activationCallback) {
		this.activationCallback = Optional.fromNullable(activationCallback);
	}

	public void activateSelection() {
		if (activationCallback.isPresent() && !getSelection().isEmpty()) {
			activationCallback.get().onCallback(getSelection().get(0).getVO());
		}
	}
}
