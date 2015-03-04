package io.pelle.mango.demo.server.test;

import io.pelle.mango.demo.server.showcase.BaseCountryEntityDAO;
import io.pelle.mango.demo.server.showcase.Country;

public class CountryEntityDAO extends BaseCountryEntityDAO {

	@Override
	public Country create(Country entity) {

		if (entity.getCountryIsoCode2() != null) {
			entity.setCountryIsoCode2(entity.getCountryIsoCode2().toUpperCase());
		}

		return super.create(entity);
	}

}
