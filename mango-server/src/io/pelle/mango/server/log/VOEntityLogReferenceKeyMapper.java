package io.pelle.mango.server.log;

import io.pelle.mango.client.base.vo.IVOEntity;

public class VOEntityLogReferenceKeyMapper implements ILogReferenceKeyMapper {

	@Override
	public String getReferenceKey(Object object) {

		assert object instanceof IVOEntity;

		IVOEntity baseVO = (IVOEntity) object;
		return baseVO.getClass().getName() + "#" + baseVO.getId();
	}

	@Override
	public boolean supports(Class<?> referenceClass) {
		return IVOEntity.class.isAssignableFrom(referenceClass);
	}

}
