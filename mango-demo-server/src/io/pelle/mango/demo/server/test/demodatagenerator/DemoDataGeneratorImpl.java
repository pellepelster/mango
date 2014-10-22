package io.pelle.mango.demo.server.test.demodatagenerator;

import io.pelle.mango.client.baseentityservice.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.client.showcase.CurrencyVO;
import io.pelle.mango.demo.client.showcase.CustomerVO;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.demodatagenerator.IDemoDataGenerator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

public class DemoDataGeneratorImpl implements IDemoDataGenerator {

	private Random rand = new Random();

	@Autowired
	private IBaseEntityService baseEntityService;

	private List<Entity2VO> initDemoDictionary2Data() {

		List<Entity2VO> result = new ArrayList<Entity2VO>();

		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("firstnames.csv");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			int i = 0;
			while ((line = br.readLine()) != null) {
				i++;

				if (i % 5 == 0) {
					Entity2VO entity2 = new Entity2VO();
					entity2.setStringDatatype2(line);
					result.add(baseEntityService.create(entity2));
				}
			}

			br.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	private int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	private void initDemoDictionary1Data(List<Entity2VO> entity2s) {

		for (int i = 0; i < 200; i++) {
			Entity1VO entity1 = new Entity1VO();
			entity1.setStringDatatype1(UUID.randomUUID().toString().substring(0, 8));
			entity1.setEntity2Datatype(entity2s.get(randInt(0, entity2s.size() - 1)));
			baseEntityService.create(entity1);
		}

	}

	public void setBaseEntityService(IBaseEntityService baseEntityService) {
		this.baseEntityService = baseEntityService;
	}

	@Override
	public void generate() {
		Map<String, CurrencyVO> currencies = createCurrencies();
		List<CountryVO> countries = createCountries(currencies);
		createCustomers(countries);

		List<Entity2VO> entity2s = initDemoDictionary2Data();
		initDemoDictionary1Data(entity2s);

	}

	private Map<String, CurrencyVO> createCurrencies() {

		Map<String, CurrencyVO> currencies = new HashMap<String, CurrencyVO>();

		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("currencies.csv");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			while ((line = br.readLine()) != null) {

				// Entity,Currency,AlphabeticCode,NumericCode,MinorUnit,WithdrawalDate,Remark
				String[] columns = line.split(",");

				if (columns.length == 5 && !currencies.containsKey(columns[2])) {
					CurrencyVO currencyVO = createCurrency(columns[1], columns[2]);
					currencies.put(currencyVO.getCurrencyIsoCode(), currencyVO);
				}

			}

			br.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return currencies;
	}

	private List<CountryVO> createCountries(Map<String, CurrencyVO> currencies) {

		List<CountryVO> result = new ArrayList<CountryVO>();

		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("countries.csv");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			while ((line = br.readLine()) != null) {

				// id;continent;name;capital;iso-2;iso-3;ioc;tld;currency;phone;utc;name_de;capital_de
				String[] columns = line.split(";");
				String currencyIsoCode = columns[8];
				if (currencies.containsKey(currencyIsoCode)) {
					result.add(createCountry(columns[2], columns[4], columns[5], currencies.get(currencyIsoCode)));
				}
			}

			br.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	private void createCustomers(List<CountryVO> countries) {

		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("names.csv");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			while ((line = br.readLine()) != null) {

				// firstname;lastname;birthdate;email;city;email;iban
				String[] columns = line.split(";");

				CountryVO countryVO = countries.get(randInt(0, countries.size() - 1));
				createCustomer(columns[0], columns[1], SimpleDateFormat.getInstance().parse(columns[2]), countryVO);
			}

			br.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private CustomerVO createCustomer(String firstName, String lastName, Date dateOfBirth, CountryVO countryVO) {
		CustomerVO customer = new CustomerVO();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setDateOfBirth(dateOfBirth);
		customer.setCountry(countryVO);
		return baseEntityService.create(customer);
	}

	private CountryVO createCountry(String name, String isoCode2, String isoCode3, CurrencyVO currencyVO) {
		CountryVO country = new CountryVO();
		country.setCountryName(name);
		country.setCountryIsoCode2(isoCode2);
		country.setCountryIsoCode3(isoCode3);
		country.setCountryCurrency(currencyVO);
		return baseEntityService.create(country);
	}

	private CurrencyVO createCurrency(String name, String isoCode3) {
		CurrencyVO currency = new CurrencyVO();
		currency.setCurrencyName(name);
		currency.setCurrencyIsoCode(isoCode3);
		return baseEntityService.create(currency);
	}

}
