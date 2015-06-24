package io.pelle.mango.demo.server;

import static org.junit.Assert.*;
import io.pelle.mango.db.util.DBUtil;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.server.vo.VOMetaDataService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoVOMetadataServiceTest extends BaseDemoTest {

	@Autowired
	private VOMetaDataService metaDataService;

	@Test
	public void testConvertVOToEntityClass() {
		
		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("xxx");

		Entity2VO entity2 = new Entity2VO();
		entity2.setStringDatatype2("xxx");
		entity1VO.setEntity2Datatype(entity2);
		
		Entity1 entity1 = (Entity1) DBUtil.convertVOToEntityClass(entity1VO);
		
		assertEquals(entity1VO.getId(), entity1.getId());
	}

}