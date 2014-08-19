package io.pelle.mango.server.validator;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.db.voquery.AttributeDescriptorAnnotation;
import io.pelle.mango.db.voquery.VOClassQuery;

import java.util.ArrayList;
import java.util.List;

public class LengthValidator implements IValidator {

	private static final String MAX_LENGTH_CONTEXT_KEY = "maxLength";

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

		for (StringAttributeDescriptor attributeDescriptor : VOClassQuery.createQuery(vo).attributesDescriptors().byType(StringAttributeDescriptor.class)) {

			Object value = vo.get(attributeDescriptor.getAttributeName());

			if (value != null && value instanceof String) {
				String valueString = (String) value;

				if (valueString.length() > attributeDescriptor.getMaxLength()) {
					result.add(new ValidationMessage(ValidatorMessages.STRING_ATTRIBUTE_MAX_LENGTH, CollectionUtils.getMap(
							IValidationMessage.ATTRIBUTE_CONTEXT_KEY, attributeDescriptor.getAttributeName(), MAX_LENGTH_CONTEXT_KEY,
							attributeDescriptor.getMaxLength())));
				}
			}

		}

		return result;
	}
}
