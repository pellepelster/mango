package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.test.client.Entity5VO;
import io.pelle.mango.test.client.ValueObject1;

import org.junit.Test;

public class ValueObjectModelGeneratorTest {

	@Test
	public void testValueObject1String() {
		ValueObject1 valueObject1 = new ValueObject1();
		valueObject1.setString1("xxx");
		assertEquals("xxx", valueObject1.getString1());
	}

	@Test
	public void testValueObject1Entity5() {
		Entity5VO entity5vo = new Entity5VO();
		ValueObject1 valueObject1 = new ValueObject1();
		valueObject1.setEntity5(entity5vo);
		assertEquals(entity5vo, valueObject1.getEntity5());
	}

}
