package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.test.Entity2;
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

	@Test
	public void testGetLabelDefaults() {
		assertEquals("io.pelle.mango.demo.client.showcase.CountryVO", metaDataService.getLabel(CountryVO.class));
		assertEquals("io.pelle.mango.demo.server.showcase.Country", metaDataService.getLabel(Country.class));
	}

	@Test
	public void testGetLabel() {
		assertEquals("Entity2 Label", metaDataService.getLabel(Entity2VO.class));
		assertEquals("Entity2 Label", metaDataService.getLabel(Entity2.class));
	}

	@Test
	public void testGetPluralLabels() {
		assertEquals("Entity2 Labels", metaDataService.getPluralLabel(Entity2VO.class));
		assertEquals("Entity2 Labels", metaDataService.getPluralLabel(Entity2.class));
	}
}
