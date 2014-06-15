package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IDateControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

public class DateControlModel extends BaseControlModel<IDateControl> implements IDateControlModel
{
	private static final long serialVersionUID = 3316617779660627072L;

	private String formatPattern;

	public DateControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getFormatPattern()
	{
		return this.formatPattern;
	}

	public void setFormatPattern(String formatPattern)
	{
		this.formatPattern = formatPattern;
	}
}
