package io.pelle.mango.demo.server;

import static org.junit.Assert.*;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.server.vo.VOMetaDataService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoEntityMetaInformationTest extends BaseDemoTest {

	@Autowired
	private VOMetaDataService metaDataService;

	@Test
	public void testGetVOClasses() {
		assertEquals(15, metaDataService.getVOClasses().size());
		assertEquals(15, metaDataService.getEntityClasses().size());
	}

	@Test
	public void testGetEntityClassForName() {
		assertEquals(Country.class, metaDataService.getEntityClassForName("country"));
		assertEquals(Country.class, metaDataService.getEntityClassForName("CoUntry"));
	}

	
}
