package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.model.BaseTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.ColumnLayout;
import io.pelle.mango.client.base.modules.dictionary.model.ColumnLayoutData;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public class AssignmentTableModel<VOType extends IBaseVO> extends BaseTableModel<VOType> implements IAssignmentTableModel {

	private static final long serialVersionUID = 1832725605229414533L;

	private String dictionaryName;

	private String attributePath;

	public AssignmentTableModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public String getAttributePath() {
		return this.attributePath;
	}

	@Override
	public String getDictionaryName() {
		return this.dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public void setAttributePath(String attributePath) {
		this.attributePath = attributePath;
	}

	@Override
	public ColumnLayoutData getLayoutData() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public ColumnLayout getLayout() {
		throw new RuntimeException("not implemented");
	}

}
