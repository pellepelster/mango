package io.pelle.mango.demo.server.vo;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.demo.client.showcase.CurrencyVO;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.showcase.ICurrencyVODAO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoCurrencyVODAOTest extends BaseDemoTest {

	@Autowired
	private ICurrencyVODAO currencyVODAO;

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Test
	public void testCountryDAOCreate() {

		CurrencyVO currency = new CurrencyVO();
		currency.setCurrencyIsoCode("gg");
		currency = currencyVODAO.create(currency);

		assertEquals("GG", currency.getCurrencyIsoCode());
	}

	@Test
	public void testBaseEntityDAOCountryCreate() {

		Country country = new Country();
		country.setCountryIsoCode2("gg");
		country = baseEntityDAO.create(country);

		assertEquals("GG", country.getCountryIsoCode2());
	}

}
