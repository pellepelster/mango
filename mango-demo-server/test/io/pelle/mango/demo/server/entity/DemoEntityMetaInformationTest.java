package io.pelle.mango.demo.server.entity;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.test.Entity2;
import io.pelle.mango.server.vo.VOMetaDataService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoEntityMetaInformationTest extends BaseDemoTest {

	@Autowired
	private VOMetaDataService metaDataService;

	@Test
	public void testGetVOClasses() {
		assertEquals(16, metaDataService.getVOClasses().size());
		assertEquals(16, metaDataService.getEntityClasses().size());
	}

	@Test
	public void testGetEntityClassForName() {
		assertEquals(Country.class, metaDataService.getEntityClassForName("country"));
		assertEquals(Country.class, metaDataService.getEntityClassForName("CoUntry"));
	}

	@Test
	@Ignore
	public void testGetLabelDefaults() {

		IEntityDescriptor<?> entityDescriptor = metaDataService.getEntityDescriptor(CountryVO.class);
		assertEquals("io.pelle.mango.demo.client.showcase.CountryVO", entityDescriptor.getLabel());

		entityDescriptor = metaDataService.getEntityDescriptor(Country.class);
		assertEquals("io.pelle.mango.demo.server.showcase.Country", entityDescriptor.getLabel());
	}

	@Test
	public void testGetLabel() {

		IEntityDescriptor<?> entityDescriptor = metaDataService.getEntityDescriptor(Entity2VO.class);
		assertEquals("Entity2 Label", entityDescriptor.getLabel());

		entityDescriptor = metaDataService.getEntityDescriptor(Entity2.class);
		assertEquals("Entity2 Label", entityDescriptor.getLabel());
	}

	@Test
	public void testGetPluralLabels() {

		IEntityDescriptor<?> entityDescriptor = metaDataService.getEntityDescriptor(Entity2VO.class);
		assertEquals("Entity2 Labels", entityDescriptor.getPluralLabel());

		entityDescriptor = metaDataService.getEntityDescriptor(Entity2.class);
		assertEquals("Entity2 Labels", entityDescriptor.getPluralLabel());
	}
}
