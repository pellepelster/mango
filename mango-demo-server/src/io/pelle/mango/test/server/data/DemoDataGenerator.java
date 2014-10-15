package io.pelle.mango.test.server.data;

import io.pelle.mango.client.baseentityservice.IBaseEntityService;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.test.client.Entity2VO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoDataGenerator implements InitializingBean {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private EntityVOMapper entityVOMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
		initDemoDictionary2Data();
	}

	private void initDemoDictionary2Data() {

		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("names.csv");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			while ((line = br.readLine()) != null) {
				Entity2VO entity2 = new Entity2VO();
				entity2.setStringDatatype2(line);
				baseEntityService.create(entity2);
			}

			br.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setEntityVOMapper(EntityVOMapper entityVOMapper) {
		this.entityVOMapper = entityVOMapper;
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService) {
		this.baseEntityService = baseEntityService;
	}

}
