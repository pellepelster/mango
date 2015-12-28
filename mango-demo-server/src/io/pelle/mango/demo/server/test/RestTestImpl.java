package io.pelle.mango.demo.server.test;

import io.pelle.mango.demo.client.test.IRestTest;
import io.pelle.mango.demo.client.test.ValueObject1;
import io.pelle.mango.demo.client.test.ValueObject2;

public class RestTestImpl implements IRestTest {

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
