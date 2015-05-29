package io.pelle.mango.demo.server;

import io.pelle.mango.server.vo.VOMetaDataService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoVOMetadataServiceTest extends BaseDemoTest {

	@Autowired
	private VOMetaDataService metaDataService;

	@Test
	public void testRemoveParent() {
		metaDataService.toString();
	}

}