package io.pelle.mango.demo.server.entity;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.showcase.ICountryEntityDAO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoCountryEntityDAOTest extends BaseDemoTest {

	@Autowired
	private ICountryEntityDAO countryEntityDAO;

	@Test
	public void testSimpleSave() {

		Country country = new Country();
		country.setCountryIsoCode2("gg");
		country = countryEntityDAO.create(country);

		assertEquals("GG", country.getCountryIsoCode2());
	}

}
