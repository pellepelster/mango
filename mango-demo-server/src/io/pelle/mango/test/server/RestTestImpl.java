package io.pelle.mango.test.server;

import io.pelle.mango.test.client.IRestTest;

public class RestTestImpl implements IRestTest {

	private boolean onOff;

	@Override
	public Boolean methodBoolean(Boolean onOff) {
		this.onOff = onOff;
		return this.onOff;
	}

}
