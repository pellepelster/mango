package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IDateControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

public class DateControlModel extends BaseControlModel<IDateControl> implements IDateControlModel {

	private static final long serialVersionUID = 3316617779660627072L;

	private DATE_FORMAT dateFormat;

	public DateControlModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	@Override
	public DATE_FORMAT getDateFormat() {
		if (dateFormat == null) {
			dateFormat = IDateControlModel.DATE_FORMAT.DATE_TIME_SHORT;
		}

		return this.dateFormat;
	}

	public void setDateFormat(DATE_FORMAT dateFormat) {
		this.dateFormat = dateFormat;
	}
}
