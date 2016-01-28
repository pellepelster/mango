package io.pelle.mango.server.validator;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.util.XPathUtil;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.db.util.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MandatoryValidator implements IValidator {

	private List<String> doNotValidateFiledNames = Arrays.asList(new String[] { IHierarchicalVO.PARENT_CLASS_FIELD_NAME,
			IHierarchicalVO.PARENT_ID_FIELD_NAME, IBaseVO.ID_FIELD_NAME, "hasChildren" });

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

		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtils.getAttributeDescriptors(vo.getClass())) {

			if (attributeDescriptor.isMandatory() && !doNotValidateFiledNames.contains(attributeDescriptor.getAttributeName())) {
				
				String fullAttributePath = XPathUtil.combine(parentPath, attributeDescriptor.getAttributeName());

				if (attributeDescriptor.getListAttributeType() == attributeDescriptor.getAttributeType()) {
					if (vo.get(attributeDescriptor.getAttributeName()) == null) {
						result.add(new ValidationMessage(ValidatorMessages.MANDATORY_ATTRIBUTE, CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY,
								fullAttributePath, IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass().getSimpleName())));
					}
				} else {
					@SuppressWarnings("rawtypes")
					List list = (List) vo.get(attributeDescriptor.getAttributeName());

					if (list.isEmpty()) {
						result.add(new ValidationMessage(ValidatorMessages.MANDATORY_LIST, CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY,
								fullAttributePath, IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass().getSimpleName())));
					}
				}
			}
		}

		return result;
	}

}
