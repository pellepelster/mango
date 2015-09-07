package io.pelle.mango.demo.server.api;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.xml.XmlVOMapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class XmlModelGeneratorTest extends BaseDemoTest {

	@Autowired
	private XmlVOMapper xmlVOMapper;
	
	
	@Test
	public void testEntity1List() {
		assertEquals("Entity1List", xmlVOMapper.getListElementName(Entity1VO.class));
	}

	@Test
	public void testEntity1() {
		assertEquals("Entity1", xmlVOMapper.getElementName(Entity1VO.class));
	}

	@Test
	public void testEntity1Reference() {
		assertEquals("Entity1Reference", xmlVOMapper.getReferenceElementName(Entity1VO.class));
	}

	@Test
	public void testEntity1ReferenceList() {
		assertEquals("Entity1ReferenceList", xmlVOMapper.getReferenceListElementName(Entity1VO.class));
	}

}
