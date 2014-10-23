package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

import com.google.common.base.Objects;

public abstract class BaseControlModel<ControlElementType> extends BaseModel<ControlElementType> implements IBaseControlModel {

	private static final long serialVersionUID = 6300062992351577766L;

	private boolean readonly;

	private String label;

	private String filterLabel;

	private String editorLabel;

	private String columnLabel;

	private boolean mandatory;

	private String toolTip;

	private String attributePath;

	public int width;

	public BaseControlModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public String getAttributePath() {
		return this.attributePath;
	}

	public void setAttributePath(String attributePath) {
		this.attributePath = attributePath;
	}

	@Override
	public String getColumnLabel() {
		return this.columnLabel;
	}

	@Override
	public String getEditorLabel() {
		return this.editorLabel;
	}

	@Override
	public String getFilterLabel() {
		return this.filterLabel;
	}

	@Override
	public String getToolTip() {
		return this.toolTip;
	}

	@Override
	public int getWidthHint() {
		return IBaseControlModel.DEFAULT_WIDTH_HINT;
	}

	@Override
	public boolean isMandatory() {
		return this.mandatory;
	}

	@Override
	public boolean isReadonly() {
		return this.readonly;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", getName()).toString();
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public void setFilterLabel(String filterLabel) {
		this.filterLabel = filterLabel;
	}

	public void setEditorLabel(String editorLabel) {
		this.editorLabel = editorLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
