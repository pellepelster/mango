package io.pelle.mango.demo.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.demo.client.test.IDemoDataGenerator;
import io.pelle.mango.demo.server.util.BaseDemoTest;

public class DemoDataGeneratorTest extends BaseDemoTest {

	@Autowired
	private IDemoDataGenerator demoDataGenerator;

	@Test
	public void testGenerate() {
		demoDataGenerator.generate();
	}
}
