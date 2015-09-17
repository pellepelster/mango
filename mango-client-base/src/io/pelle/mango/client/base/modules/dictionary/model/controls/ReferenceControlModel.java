package io.pelle.mango.client.base.modules.dictionary.model.controls;

import java.util.ArrayList;
import java.util.List;

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public class ReferenceControlModel<VOType extends IBaseVO> extends BaseControlModel<IReferenceControl<VOType>>implements IReferenceControlModel {

	private static final long serialVersionUID = -9089131170958607211L;

	private String dictionaryName;

	private List<IBaseControlModel> labelControls = new ArrayList<IBaseControlModel>();

	private CONTROL_TYPE controlType = CONTROL_TYPE.TEXTCONTROL;

	private int suggestionsLimit = DEFAULT_SUGGESTIONS_LIMIT;

	public ReferenceControlModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	@Override
	public String getDictionaryName() {
		return this.dictionaryName;
	}

	@Override
	public List<IBaseControlModel> getLabelControls() {
		return this.labelControls;
	}

	@Override
	public CONTROL_TYPE getControlType() {
		return this.controlType;
	}

	public void setControlType(CONTROL_TYPE controlType) {
		this.controlType = controlType;
	}

	@Override
	public int getSuggestionsLimit() {
		return suggestionsLimit;
	}

	public void setSuggestionsLimit(int suggestionsLimit) {
		this.suggestionsLimit = suggestionsLimit;
	}
}
