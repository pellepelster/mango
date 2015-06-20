package io.pelle.mango.server.validator;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.BaseVODAO;
import io.pelle.mango.db.voquery.VOClassQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Checks all {@link IBaseVO} derived Vo's whether they contain values in
 * {@link NaturalKey} annotated properties that are already present in the
 * database
 * 
 * @author pelle
 * 
 */
public class NaturalKeyValidator implements IValidator {

	@Autowired
	private BaseVODAO baseVODAO;

	public NaturalKeyValidator() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o) {
		return IBaseVO.class.isAssignableFrom(o.getClass());
	}

	protected void setBaseVODAO(BaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}

	@SuppressWarnings("rawtypes")
	public boolean populateNaturalKeyQuery(IBaseVO vo, IAttributeDescriptor<?> parentAttribute, SelectQuery<IBaseVO> selectQuery, List<IValidationMessage> messages, Map<String, Object> contextMap) {

		boolean hasNaturalKeys = false;

		for (IAttributeDescriptor naturalKeyAttributeDescriptor : VOClassQuery.createQuery(vo.getClass()).attributesDescriptors().naturalKeys()) {

			hasNaturalKeys = true;
			Object naturalKeyValue = vo.get(naturalKeyAttributeDescriptor.getAttributeName());

			if (!contextMap.containsKey(IValidationMessage.ATTRIBUTE_CONTEXT_KEY)) {
				contextMap.put(IValidationMessage.ATTRIBUTE_CONTEXT_KEY, naturalKeyAttributeDescriptor.getAttributeName());
			}

			if (naturalKeyValue == null) {
				messages.add(new ValidationMessage(ValidatorMessages.NATURAL_KEY_MANDATORY, contextMap));
			} else {

				if (IBaseVO.class.isAssignableFrom(naturalKeyAttributeDescriptor.getListAttributeType())) {
					populateNaturalKeyQuery((IBaseVO) naturalKeyValue, naturalKeyAttributeDescriptor, selectQuery, messages, contextMap);
				} else {
					if (parentAttribute == null) {
						selectQuery.addWhereAnd(naturalKeyAttributeDescriptor.eq(naturalKeyValue));
					} else {
						selectQuery.addWhereAnd(parentAttribute.path(naturalKeyAttributeDescriptor).eq(naturalKeyValue));
					}
				}
			}
		}

		return hasNaturalKeys;
	}

	@Override
	public List<IValidationMessage> validate(Object o) {

		IBaseVO vo = (IBaseVO) o;
		Map<String, Object> contextMap = new HashMap<String, Object>();

		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		SelectQuery<IBaseVO> selectQuery = (SelectQuery<IBaseVO>) SelectQuery.selectFrom(vo.getClass());

		contextMap.put(IValidationMessage.NATURAL_KEY_CONTEXT_KEY, vo.getNaturalKey());
		contextMap.put(IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass().getName());

		boolean doQuery = populateNaturalKeyQuery(vo, null, selectQuery, result, contextMap);

		if (doQuery) {
			List<IBaseVO> filterResult = this.baseVODAO.filter(selectQuery);

			if (filterResult.size() > 1 || (filterResult.size() == 1 && filterResult.get(0).getOid() != vo.getOid())) {
				result.add(new ValidationMessage(ValidatorMessages.NATURAL_KEY, contextMap));
			}
		}

		return result;
	}
}
