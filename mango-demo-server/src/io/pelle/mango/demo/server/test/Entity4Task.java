package io.pelle.mango.demo.server.test;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.test.Entity4VO;

public class Entity4Task {

	@Autowired
	private IBaseEntityService baseEntityService;

	public void createEntity4() {

		Entity4VO entity4 = new Entity4VO();
		entity4.setStringDatatype4(UUID.randomUUID().toString());

		baseEntityService.create(entity4);
	}
}
