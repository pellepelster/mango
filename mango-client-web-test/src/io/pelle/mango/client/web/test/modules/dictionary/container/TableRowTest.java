package io.pelle.mango.client.web.test.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.test.modules.dictionary.controls.BaseControlTest;

public class TableRowTest<VOType extends IBaseVO> {

	private IBaseTable.ITableRow<VOType> tableRow;

	public TableRowTest(IBaseTable.ITableRow<VOType> tableRow) {
		this.tableRow = tableRow;
	}

	public <ElementType extends IBaseControl<Value, ?>, Value extends Object> BaseControlTest<ElementType, Value> getBaseControlTestElement(BaseModel<ElementType> baseModel) {
		return new BaseControlTest<ElementType, Value>(this.tableRow.getElement(baseModel));
	}

}
