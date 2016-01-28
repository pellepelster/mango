package io.pelle.mango.demo.server.resttest2;

import io.pelle.mango.demo.client.resttest2.IRestService2;
import io.pelle.mango.demo.client.test.ValueObject1;
import io.pelle.mango.demo.client.test.ValueObject2;

public class RestService2Impl implements IRestService2 {

	private boolean onOff;

	@Override
	public Boolean methodWithBooleanParameter(Boolean onOff) {
		this.onOff = !onOff;
		return this.onOff;
	}

	@Override
	public ValueObject1 methodWithValueObjectParameter(ValueObject2 valueObject2) {
		ValueObject1 result = new ValueObject1();
		result.setString1(valueObject2.getString2());
		return result;
	}

}
