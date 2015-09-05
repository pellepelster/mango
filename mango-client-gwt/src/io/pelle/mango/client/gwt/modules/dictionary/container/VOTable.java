package io.pelle.mango.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ControlHandler;

public class VOTable<VOType extends IBaseVO> extends DataGrid<VOType> {

	private final ListDataProvider<VOType> dataProvider = new ListDataProvider<VOType>();

	private List<IBaseControlModel> baseControlModels;

	private final SingleSelectionModel<VOType> selectionModel;

	public VOTable(List<IBaseControlModel> baseControlModels) {

		this.baseControlModels = baseControlModels;

		selectionModel = new SingleSelectionModel<VOType>(getKeyProvider());
		setSelectionModel(selectionModel);

		dataProvider.addDataDisplay(this);
		createModelColumns();
	}

	public void addSelectHandler(final SimpleCallback<VOType> selectHandler) {
		addDomHandler(new DoubleClickHandler() {

			/** {@inheritDoc} */
			@Override
			public void onDoubleClick(DoubleClickEvent event) {

				if (selectionModel.getSelectedObject() != null) {
					selectHandler.onCallback(selectionModel.getSelectedObject());
				}
			}
		}, DoubleClickEvent.getType());

	}

	protected void createModelColumns() {

		for (final IBaseControlModel baseControlModel : baseControlModels) {

			Header<?> header = ControlHandler.getInstance().createHeader(baseControlModel);
			Column<VOType, ?> column = ControlHandler.getInstance().createColumn(baseControlModel);

			addColumn(column, header);

		}

	}

	public void setContent(List<VOType> list) {
		dataProvider.setList(list);
	}

	public VOType getCurrentSelection() {
		return selectionModel.getSelectedObject();
	}

}
