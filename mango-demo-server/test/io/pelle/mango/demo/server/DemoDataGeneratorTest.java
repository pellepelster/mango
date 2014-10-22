package io.pelle.mango.demo.server;

import io.pelle.mango.demo.client.test.demodatagenerator.IDemoDataGenerator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.editor.client.Editor.Ignore;

public class DemoDataGeneratorTest extends BaseDemoTest {

	@Autowired
	private IDemoDataGenerator demoDataGenerator;

	@Test
	@Ignore
	public void testGenerate() {
		demoDataGenerator.generate();
	}

	public void setDemoDataGenerator(IDemoDataGenerator demoDataGenerator) {
		this.demoDataGenerator = demoDataGenerator;
	}

}
