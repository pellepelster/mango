package io.pelle.mango.demo.server.test.demodatagenerator;

import io.pelle.mango.client.baseentityservice.IBaseEntityService;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.demodatagenerator.IDemoDataGenerator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("names.csv");

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
		List<Entity2VO> entity2s = initDemoDictionary2Data();
		initDemoDictionary1Data(entity2s);
	}

}
