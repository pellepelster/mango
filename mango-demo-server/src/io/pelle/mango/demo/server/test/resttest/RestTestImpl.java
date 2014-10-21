package io.pelle.mango.demo.server.test.resttest;

import io.pelle.mango.demo.client.test.ValueObject2;
import io.pelle.mango.demo.client.test.resttest.IRestTest;

public class RestTestImpl implements IRestTest {

	private boolean onOff;

	@Override
	public Boolean methodWithBooleanParameter(Boolean onOff) {
		this.onOff = !onOff;
		return this.onOff;
	}

	@Override
	public ValueObject2 methodWithValueObjectParameter(ValueObject2 valueObject2) {
		return valueObject2;
	}

}
