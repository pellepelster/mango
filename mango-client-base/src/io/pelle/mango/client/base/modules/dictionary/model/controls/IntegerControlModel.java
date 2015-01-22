package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IIntegerControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

public class IntegerControlModel extends BaseControlModel<IIntegerControl> implements IIntegerControlModel {

	private static final long serialVersionUID = 3758940598822644209L;

	private Integer max = MAX_DEFAULT;

	private Integer min = MIN_DEFAULT;

	private CONTROL_TYPE controlType = CONTROL_TYPE.TEXTCONTROL;

	public IntegerControlModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public Integer getMax() {
		return this.max;
	}

	@Override
	public Integer getMin() {
		return this.min;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	@Override
	public CONTROL_TYPE getControlType() {
		return controlType;
	}

	public void setControlType(CONTROL_TYPE controlType) {
		this.controlType = controlType;
	}
}
