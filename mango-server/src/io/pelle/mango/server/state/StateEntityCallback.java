package io.pelle.mango.server.state;

import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.dao.IEntityCallback;
import io.pelle.mango.server.base.BaseEntity;

public class StateEntityCallback implements IEntityCallback {

	@Autowired
	private StateServiceImpl stateServiceImpl;

	@Override
	public void beforeCreate(IBaseEntity entity) {

		StateBuilder sb = stateServiceImpl.getStateBuilder(entity);
		
		if (sb != null && sb.getCurrentState(entity) == null) {
			sb.setInitalState(entity);
		}
		
	}

	@Override
	public boolean supports(Class<? extends IBaseEntity> entityClass) {
		return BaseEntity.class.isAssignableFrom(entityClass);
	}

	@Override
	public void beforeSave(IBaseEntity entity) {
		beforeCreate(entity);
	}

}
