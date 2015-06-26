

import io.pelle.mango.demo.MangoDemoCli;

import org.junit.Test;

public class TestMangoDemoCli {

	@Test
	public void testClient(){
		
		MangoDemoCli.main(new String[] { "-url", "http://localhost:9090"});
		
	}
	
}
