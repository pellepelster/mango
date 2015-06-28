package io.pelle.mango.demo.server.test;

import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CUSTOMERTITLE;
import io.pelle.mango.demo.client.showcase.CompanyVO;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.demo.client.showcase.CurrencyVO;
import io.pelle.mango.demo.client.showcase.CustomerVO;
import io.pelle.mango.demo.client.showcase.DepartmentVO;
import io.pelle.mango.demo.client.showcase.EmployeeVO;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.IDemoDataGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

	private int randomInteger(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	private boolean randomBoolean() {
		return randomInteger(0, 1) == 1;
	}

	private CUSTOMERTITLE randomTitle() {
		if (randomBoolean()) {
			return CUSTOMERTITLE.MRS;
		} else {
			return CUSTOMERTITLE.MR;
		}
	}

	private void initDemoDictionary1Data(List<Entity2VO> entity2s) {

		for (int i = 0; i < 200; i++) {
			Entity1VO entity1 = new Entity1VO();
			entity1.setStringDatatype1(UUID.randomUUID().toString().substring(0, 8));
			entity1.setEntity2Datatype(entity2s.get(randomInteger(0, entity2s.size() - 1)));
			baseEntityService.create(entity1);
		}

	}

	public void setBaseEntityService(IBaseEntityService baseEntityService) {
		this.baseEntityService = baseEntityService;
	}

	@Override
	@Transactional
	public void generate() {

		try {

			Map<String, CurrencyVO> currencies = createCurrencies();
			List<CountryVO> countries = createCountries(currencies);

			createCustomers(countries);

			List<Entity2VO> entity2s = initDemoDictionary2Data();
			initDemoDictionary1Data(entity2s);

			List<CompanyVO> companies = createCompanies();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

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

	private List<CompanyVO> createCompanies() throws IOException {

		List<CompanyVO> companies = new ArrayList<>();

		InputStream in = this.getClass().getClassLoader().getResourceAsStream("companies.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;

		while ((line = br.readLine()) != null) {
			String[] columns = line.split(",");
			CompanyVO company = new CompanyVO();
			company.setName(columns[0]);
			companies.add(company);
		}
		br.close();

		Collections.shuffle(companies);

		List<CompanyVO> result = new ArrayList<>();

		for (int i = 0; i < 10; i++) {

			CompanyVO company = baseEntityService.create(companies.get(i));
			result.add(company);

			createDepartments(company);

		}

		return companies;
	}

	private List<DepartmentVO> createDepartments(CompanyVO company) throws IOException {

		List<DepartmentVO> departments = new ArrayList<>();

		InputStream in = this.getClass().getClassLoader().getResourceAsStream("departments.csv");

		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;

		while ((line = br.readLine()) != null) {

			String[] columns = line.split(",");

			DepartmentVO department = new DepartmentVO();
			department.setName(columns[0]);
			departments.add(department);
			department.setParent(company);
		}
		br.close();

		Collections.shuffle(departments);

		for (int i = 0; i < 5; i++) {

			DepartmentVO department = baseEntityService.create(departments.get(i));
			createEmployees(department);

		}

		return departments;
	}

	private void createEmployees(DepartmentVO department) throws IOException {

		List<EmployeeVO> employees = new ArrayList<>();

		InputStream in = this.getClass().getClassLoader().getResourceAsStream("names.csv");

		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;

		while ((line = br.readLine()) != null) {

			// firstname;lastname;birthdate;email;city;email;iban
			String[] columns = line.split(";");

			EmployeeVO employee = new EmployeeVO();
			employee.setName(columns[0] + " " + columns[1]);
			employee.setParent(department);
			employees.add(employee);
		}
		br.close();

		Collections.shuffle(employees);

		for (int i = 0; i < 10; i++) {

			EmployeeVO employee = baseEntityService.create(employees.get(i));

		}

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

		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy");
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("names.csv");

			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			while ((line = br.readLine()) != null) {

				// firstname;lastname;birthdate;email;city;email;iban
				String[] columns = line.split(";");

				CountryVO countryVO = countries.get(randomInteger(0, countries.size() - 1));
				createCustomer(columns[0], columns[1], format.parse(columns[2]), countryVO);
				createCustomer(columns[0], columns[1], formatter.parse(columns[2]), countryVO);
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
		customer.setSendBirthdayCards(randomBoolean());
		customer.setTitle(randomTitle());
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
