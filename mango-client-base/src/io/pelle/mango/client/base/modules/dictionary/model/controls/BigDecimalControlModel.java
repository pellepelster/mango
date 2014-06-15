package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IBigDecimalControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

public class BigDecimalControlModel extends BaseControlModel<IBigDecimalControl> implements IBigDecimalControlModel
{

	private static final long serialVersionUID = 1699242302211790344L;

	private int fractionDigits = FRACTION_DIGITS_DEFAULT;

	private int totalDigits = TOTAL_DIGITS_DEFAULT;

	public BigDecimalControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public int getFractionDigits()
	{
		return this.fractionDigits;
	}

	@Override
	public int getTotalDigits()
	{
		return this.totalDigits;
	}

	public void setFractionDigits(int fractionDigits)
	{
		this.fractionDigits = fractionDigits;
	}

	public void setTotalDigits(int totalDigits)
	{
		this.totalDigits = totalDigits;
	}

}
