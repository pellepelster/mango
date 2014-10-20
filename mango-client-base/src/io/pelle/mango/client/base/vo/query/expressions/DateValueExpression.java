package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.MangoClientBase;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;
import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

import java.io.Serializable;
import java.util.Date;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class DateValueExpression implements IExpression, Serializable {

	private Date value;

	public DateValueExpression() {
	}

	public DateValueExpression(Date value) {
		this.value = value;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return "'" + MangoClientBase.getInstance().getValueConverter().formatDate(value, IDateControlModel.DATE_FORMAT.MONTH_DAY_YEAR) + "'";
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("value", value).toString();
	}

}