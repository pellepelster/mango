package io.pelle.mango.server.util;

import io.pelle.mango.client.base.db.vos.NaturalKey;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.server.validator.AnnotationIterator;

import java.util.ArrayList;
import java.util.List;

public class NaturalKeyUtils {
	
	public static List<IAttributeDescriptor<?>> getNaturalKeys(Class<? extends IBaseVO> voClass) {
		List<IAttributeDescriptor<?>> naturalKeys = new ArrayList<IAttributeDescriptor<?>>();

		for (IAttributeDescriptor<?> attributeDescriptor : new AnnotationIterator(voClass, NaturalKey.class)) {
			naturalKeys.add(attributeDescriptor);
		}

		return naturalKeys;
	}
}