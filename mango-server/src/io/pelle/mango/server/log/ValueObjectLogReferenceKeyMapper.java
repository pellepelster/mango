package io.pelle.mango.server.log;

import io.pelle.mango.client.base.vo.IBaseVO;

public class ValueObjectLogReferenceKeyMapper implements ILogReferenceKeyMapper {

	@Override
	public String getReferenceKey(Object object) {

		assert object instanceof IBaseVO;

		IBaseVO baseVO = (IBaseVO) object;
		return baseVO.getClass().getName() + "#" + baseVO.getId();
	}

	@Override
	public boolean supports(Class<?> referenceClass) {
		return IBaseVO.class.isAssignableFrom(referenceClass);
	}

}
