package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.search.ISearchService;
import io.pelle.mango.client.search.SearchResultItem;
import io.pelle.mango.demo.client.showcase.CompanyVO;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchServiceTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private ISearchService searchService;

	@Test
	public void testSearchEntity1() {

		CompanyVO company1 = new CompanyVO();
		company1.setName("aaa");
		company1 = baseEntityService.create(company1);

		CompanyVO company2 = new CompanyVO();
		company2.setName("bbb");
		company2 = baseEntityService.create(company2);
		
		List<SearchResultItem> result = searchService.search("index1", "bbb");
		assertEquals(1, result.size());
		assertEquals((Long)company2.getId(), result.get(0).getId());
	}
}
