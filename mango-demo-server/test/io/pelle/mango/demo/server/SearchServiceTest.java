package io.pelle.mango.demo.server;

import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.test.Entity1VO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchServiceTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testSearchEntity1() {

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");
		baseEntityService.create(entity1VO);

		entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("bbb");
		baseEntityService.create(entity1VO);
	}
}
