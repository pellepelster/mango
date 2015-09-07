package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.search.ISearchService;
import io.pelle.mango.client.search.SearchResultItem;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.demo.client.showcase.CompanyVO;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.server.util.BaseDemoTest;

public class SearchServiceTest extends BaseDemoTest {

	@Autowired
	private IBaseVODAO baseVODAO;

	@Autowired
	private ISearchService searchService;

	@Before
	public void deleteAll() {
		baseVODAO.deleteAll(CompanyVO.class);
		baseVODAO.deleteAll(CountryVO.class);
	}

	@Test
	public void testSearchIndex1Limit() {

		for (int i = 0; i < 100; i++) {
			CountryVO country = new CountryVO();
			country.setCountryName("aa country" + i);
			baseVODAO.create(country);
		}

		for (int i = 0; i < 100; i++) {
			CompanyVO company = new CompanyVO();
			company.setName("aa company" + i);
			baseVODAO.create(company);
		}

		List<SearchResultItem> result = searchService.search("index1", "aa");
		assertEquals(50, result.size());
	}

	@Test
	public void testSearchIndex1CaseInsensivity() {

		CountryVO country1 = new CountryVO();
		country1.setCountryName("aa country");
		country1 = baseVODAO.create(country1);

		CountryVO country2 = new CountryVO();
		country2.setCountryName("AA country");
		country2 = baseVODAO.create(country2);

		List<SearchResultItem> result = searchService.search("index1", "aa");
		assertEquals(2, result.size());
	}

	@Test
	public void testSearchIndex1() {

		CountryVO country1 = new CountryVO();
		country1.setCountryName("aa country");
		country1 = baseVODAO.create(country1);

		CountryVO country2 = new CountryVO();
		country2.setCountryName("cc country");
		country2 = baseVODAO.create(country2);

		// CompanyVO company1 = new CompanyVO();
		// company1.setName("aa company");
		// company1 = baseEntityService.create(company1);
		//
		// CompanyVO company2 = new CompanyVO();
		// company2.setName("bb company");
		// company2 = baseEntityService.create(company2);

		List<SearchResultItem> result = searchService.search("index1", "cc");
		assertEquals(1, result.size());

		// assertEquals((Long) company2.getId(), result.get(0).getId());

		// SearchResultItem company2SearchResult = result.get(0);
		// company2SearchResult.toString();

		assertEquals((Long) country2.getId(), result.get(0).getId());
	}

	@Test
	public void testSearchDefaultIndex() {

		CompanyVO company1 = new CompanyVO();
		company1.setName("aaa");
		company1 = baseVODAO.create(company1);

		CompanyVO company2 = new CompanyVO();
		company2.setName("bbb");
		company2 = baseVODAO.create(company2);

		List<SearchResultItem> result = searchService.searchDefaultIndex("bbb");
		assertEquals(1, result.size());
		assertEquals((Long) company2.getId(), result.get(0).getId());
	}
}
