package io.pelle.mango.client;

import io.pelle.mango.cli.BaseMangoCli;

import org.junit.Ignore;
import org.junit.Test;

public class TestBaseMangoCli {

	@Test
	@Ignore
	public void testClient(){
		
		BaseMangoCli.create(TestApplicationContext.class, new String[] { "-url", "http://localhost:9090"});
		
	}
	
}
