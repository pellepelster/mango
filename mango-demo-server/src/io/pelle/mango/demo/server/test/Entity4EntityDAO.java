package io.pelle.mango.demo.server.test;

import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.demo.client.DemoClient;

import org.springframework.beans.factory.annotation.Autowired;

public class Entity4EntityDAO extends BaseEntity4EntityDAO {

	@Autowired
	private IPropertyService propertyService;

	@Override
	public Entity4 create(Entity4 entity) {

		if (propertyService.getProperty(DemoClient.ACTIVATE_ENTITY1_CREATE_FEATURE)) {

			try {
				Thread.sleep(propertyService.getProperty(DemoClient.ENTITY1_CREATE_FEATURE_WAIT_TIME));
			} catch (InterruptedException e) {
				// do something meaningful here
			}

		}

		return super.create(entity);
	}

}
