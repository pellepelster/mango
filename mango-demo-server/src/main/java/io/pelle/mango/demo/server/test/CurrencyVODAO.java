package io.pelle.mango.demo.server.test;

import io.pelle.mango.demo.client.showcase.CurrencyVO;
import io.pelle.mango.demo.server.showcase.BaseCurrencyVODAO;

public class CurrencyVODAO extends BaseCurrencyVODAO {

	@Override
	public CurrencyVO create(CurrencyVO entity) {

		if (entity.getCurrencyIsoCode() != null) {
			entity.setCurrencyIsoCode(entity.getCurrencyIsoCode().toUpperCase());
		}

		return super.create(entity);
	}

}
