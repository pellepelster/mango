package io.pelle.mango.client.web.test.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IDateControl;

import java.util.Date;

import junit.framework.Assert;

public class DateControlTest extends BaseControlTest<IDateControl, Date> {

	public DateControlTest(IDateControl dateControl) {
		super(dateControl);
	}

	public void assertValueWithoutMillies(Date expectedValue) {
		Assert.assertEquals(expectedValue.getTime() / 1000, getValue().getTime() / 1000);
	}

}
