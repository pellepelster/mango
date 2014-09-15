package io.pelle.mango.test.server.resttest;

import io.pelle.mango.test.client.resttest.IRestTest;

public class RestTestImpl implements IRestTest {

	private boolean onOff;

	@Override
	public Boolean methodWithBooleanParameter(Boolean onOff) {
		this.onOff = !onOff;
		return this.onOff;
	}

}
