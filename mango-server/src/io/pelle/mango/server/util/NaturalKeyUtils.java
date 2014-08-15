package io.pelle.mango.server.util;

import io.pelle.mango.client.base.db.vos.NaturalKey;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.db.vo.AttributeDescriptorAnnotation;
import io.pelle.mango.db.vo.VOClassQuery;

import java.util.ArrayList;
import java.util.List;

public class NaturalKeyUtils {

	public static List<IAttributeDescriptor<?>> getNaturalKeys(Class<? extends IBaseVO> voClass) {

		List<IAttributeDescriptor<?>> naturalKeys = new ArrayList<IAttributeDescriptor<?>>();

		for (AttributeDescriptorAnnotation<NaturalKey> attributeDescriptorAnnotation : VOClassQuery.createQuery(voClass).attributesDescriptors().byAnnotation(NaturalKey.class)) {
			naturalKeys.add(attributeDescriptorAnnotation.getAttributeDescriptor());
		}

		return naturalKeys;
	}
}