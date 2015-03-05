package io.pelle.mango.demo.server.entity;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.showcase.ICountryEntityDAO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoCountryEntityDAOTest extends BaseDemoTest {

	@Autowired
	private ICountryEntityDAO countryEntityDAO;

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Test
	public void testCountryDAOCreate() {

		Country country = new Country();
		country.setCountryIsoCode2("gg");
		country = countryEntityDAO.create(country);

		assertEquals("GG", country.getCountryIsoCode2());
	}

	@Test
	public void testBaseEntityDAOCountryCreate() {

		Country country = new Country();
		country.setCountryIsoCode2("gg");
		country = baseEntityDAO.create(country);

		assertEquals("GG", country.getCountryIsoCode2());
	}

}
