package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IControlGroup;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public abstract class ControlGroupModel extends BaseControlModel<IControlGroup> implements IControlGroupModel {

	private static final long serialVersionUID = 532719232119633708L;

	private boolean isMultiFilterField = false;

	private List<IBaseControlModel> controls = new ArrayList<IBaseControlModel>();

	public ControlGroupModel(String name, IBaseModel parent, boolean isMultiFilterField) {
		super(name, parent);
		this.isMultiFilterField = isMultiFilterField;
	}

	public List<IBaseControlModel> getControls() {
		return controls;
	}

	@Override
	public boolean hasAttributePath() {
		return Iterables.any(controls, new Predicate<IBaseControlModel>() {
			@Override
			public boolean apply(IBaseControlModel input) {
				return input.hasAttributePath();
			}
		});
	}

	@Override
	public String getAttributePath() {
		return null;
	}

	@Override
	public boolean isMultiFilterField() {
		return isMultiFilterField;
	}

}
