package io.pelle.mango.server.validator;

import io.pelle.mango.client.base.db.vos.Mandatory;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.util.XPathUtil;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.db.util.BeanUtils;
import io.pelle.mango.db.vo.AttributeDescriptorAnnotation;
import io.pelle.mango.db.vo.VOClassQuery;

import java.util.ArrayList;
import java.util.List;

public class MandatoryValidator implements IValidator {
	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o) {
		return IBaseVO.class.isAssignableFrom(o.getClass());
	}

	@Override
	public List<IValidationMessage> validate(Object o) {
		return validateInternal(o, "");
	}

	public List<IValidationMessage> validateInternal(Object o, String parentPath) {
		IBaseVO vo = (IBaseVO) o;

		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		for (AttributeDescriptorAnnotation<Mandatory> attributeDescriptorAnnotation : VOClassQuery.createQuery(vo.getClass()).attributesDescriptors().byAnnotation(Mandatory.class)) {

			String fullAttributePath = XPathUtil.combine(parentPath, attributeDescriptorAnnotation.getAttributeDescriptor().getAttributeName());

			if (attributeDescriptorAnnotation.getAttributeDescriptor().getListAttributeType() == null) {
				if (vo.get(attributeDescriptorAnnotation.getAttributeDescriptor().getAttributeName()) == null) {
					result.add(new ValidationMessage(ValidatorMessages.MANDATORY_ATTRIBUTE, CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY, fullAttributePath, IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass()
							.getSimpleName())));
				}
			} else {
				@SuppressWarnings("unchecked")
				List<IBaseVO> list = (List<IBaseVO>) vo.get(attributeDescriptorAnnotation.getAttributeDescriptor().getAttributeName());

				if (list.isEmpty()) {
					result.add(new ValidationMessage(ValidatorMessages.MANDATORY_LIST, CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY, fullAttributePath, IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass()
							.getSimpleName())));
				}
			}
		}

		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtils.getAttributeDescriptors(vo.getClass())) {

			if (attributeDescriptor.getListAttributeType() != null) {

				@SuppressWarnings("unchecked")
				List<IBaseVO> list = (List<IBaseVO>) vo.get(attributeDescriptor.getAttributeName());

				for (IBaseVO listItem : list) {
					result.addAll(validateInternal(listItem, XPathUtil.combine(parentPath, attributeDescriptor.getAttributeName()) + String.format("[oid=%d]", listItem.getOid())));
				}
			}
		}

		return result;
	}

}
