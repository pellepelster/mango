package io.pelle.mango.client.base.modules.dictionary.model;

import io.pelle.mango.client.base.modules.dictionary.hooks.BaseTableHook;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseTableModel<ElementType extends IBaseVO> extends BaseModel<ElementType> implements IBaseTableModel {

	private List<IBaseControlModel> columns = new ArrayList<IBaseControlModel>();

	private static final long serialVersionUID = 7452528927479882166L;

	private Integer visibleRows = DEFAULT_VISBLE_ROWS;

	private int paingSize = DEFAULT_PAGING_SIZE;

	public BaseTableModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public Integer getVisibleRows() {
		return this.visibleRows;
	}

	public void setTableHook(BaseTableHook<ElementType> baseTableHook) {
		DictionaryHookRegistry.getInstance().addTableHook(this, baseTableHook);
	}

	@Override
	public List<IBaseContainerModel> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public List<IBaseControlModel> getControls() {
		return this.columns;
	}

	@Override
	public int getPagingSize() {
		return this.paingSize;
	}

}
