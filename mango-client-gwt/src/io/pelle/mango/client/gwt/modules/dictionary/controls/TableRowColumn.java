package io.pelle.mango.client.gwt.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

public class TableRowColumn<VOType extends IBaseVO> extends Column<IBaseTable.ITableRow<VOType>, String>
{
	private IBaseControlModel baseControlModel;

	public TableRowColumn(IBaseControlModel baseControlModel, Cell<String> cell)
	{
		super(cell);
		this.baseControlModel = baseControlModel;
	}

	@Override
	public String getValue(IBaseTable.ITableRow<VOType> tableRow)
	{
		return tableRow.getElement(baseControlModel).format();
	}
};