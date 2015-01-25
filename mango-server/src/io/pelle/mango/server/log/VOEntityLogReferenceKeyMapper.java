package io.pelle.mango.server.log;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.db.util.EntityVOMapper;

public class VOEntityLogReferenceKeyMapper implements ILogReferenceKeyMapper {

	@Override
	public String getReferenceKey(Object object) {

		assert object instanceof IVOEntity;

		IVOEntity baseVO = (IVOEntity) object;

		Class<?> entityClass = object.getClass();

		if (baseVO instanceof IBaseVO) {
			entityClass = EntityVOMapper.getInstance().getEntityClass(entityClass);
		}

		return entityClass.getName() + "#" + baseVO.getId();
	}

	@Override
	public boolean supports(Class<?> referenceClass) {
		return IVOEntity.class.isAssignableFrom(referenceClass);
	}

}
